package com.estore.api.estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.estore.api.estoreapi.model.Order;

/**
 * Implements the functionality for JSON file-based peristance for Orders
 * 
 * {@literal @}Component Spring annotation instantiates a single instance of
 * this
 * class and injects the instance into other classes as needed
 * 
 * @author Zak Rutherford
 */
@Component
public class OrderFileDAO implements OrderDAO {
    private static final Logger LOG = Logger.getLogger(OrderFileDAO.class.getName());
    public Map<Integer, Order> orders; // Provides a local cache of the Order objects
                                    // so that we don't need to read from the file
                                    // each time
    private ObjectMapper objectMapper; // Provides conversion between Orders
                                       // objects and JSON text format written
                                       // to the file
    private static int currentID; // The lates ID to be assigned to an order
    private static ArrayList<Integer> deletedIDs; // arraylist of all deleted ids
    private String filename; // Filename to read from and write to

    /**
     * Creates a Order File Data Access Object
     * 
     * @param filename     Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization
     *                     and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public OrderFileDAO(@Value("${orders.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load(); // load the orders from the file
    }

    /**
     * Generates the next id for a new {@linkplain Order order}
     * 
     * @return The an id
     */
    private synchronized static int nextId() {
        if (deletedIDs.size() > 0) {
            return deletedIDs.remove(0);
        }
        else {
            currentID++;
            return currentID;
        }
    }

    /**
     * Generates an array of {@linkplain Order orders} from the tree map
     * 
     * @return The array of {@link Order orders}, may be empty
     */
    private Order[] getOrdersArray() {
        return getOrdersArray(null);
    }

    /**
     * Generates an array of {@linkplain Order orders} from the tree map for any
     * {@linkplain Order orders} that contains the text specified by
     * containsText
     * <br>
     * If containsText is null, the array contains all of the {@linkplain Order
     * orders}
     * in the tree map
     * 
     * @return The array of {@link Order orders}, may be empty
     */
    private Order[] getOrdersArray(String containsText) { // if containsText == null, no filter
        ArrayList<Order> orderArrayList = new ArrayList<>();

        for (Order order : orders.values()) {
            if (containsText == null || order.getEmail().contains(containsText)) {
                orderArrayList.add(order);
            }
        }

        Order[] orderArray = new Order[orderArrayList.size()];
        orderArrayList.toArray(orderArray);
        return orderArray;
    }

    /**
     * Saves the {@linkplain Order orders} from the map into the file as an
     * array of JSON objects
     * 
     * @return true if the {@link Order orders} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        Order[] orderArray = getOrdersArray();

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename), orderArray);
        return true;
    }

    /**
     * Loads {@linkplain Order orders} from the JSON file into the map
     * <br>
     * Also sets next id to one more than the greatest id found in the file
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        orders = new TreeMap<>();
        currentID = 0;
        deletedIDs = new ArrayList<Integer>();

        // Deserializes the JSON objects from the file into an array of orders
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        Order[] orderArray = objectMapper.readValue(new File(filename), Order[].class);

        // Add each order to the tree map and keep track of the greatest id
        int maxID = 0;
        for (Order order : orderArray) {
            orders.put(order.getId(), order);
            if (order.getId() > maxID)
                maxID = order.getId();
        }

        //assign the value.
        currentID = maxID;

        //find the missing ids to fil in the deleted id list
        for (int i = 1; i <= maxID; i++) {
            deletedIDs.add(i);
        }

        for (Order order : orderArray) {
            deletedIDs.remove(deletedIDs.indexOf(order.getId()));
        }

        return true;
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public Order[] getOrders() {
        synchronized (orders) {
            return getOrdersArray();
        }
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public Order[] findOrders(String containsText) {
        synchronized (orders) {
            String searchLowerCase = containsText.toLowerCase();
            List<Order> matches = new ArrayList<>();
            for (Order order : getOrdersArray()) {
                if (order.getEmail().toLowerCase().contains(searchLowerCase)) {
                    matches.add(order);
                }
            }
            return matches.toArray(new Order[0]);
        }
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public Order getOrder(int id) {
        synchronized (orders) {
            if (orders.containsKey(id))
                return orders.get(id);
            else
                return null;
        }
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public Order createOrder(Order order) throws IOException {
        synchronized (orders) {
            // We create a new order object because the id field is immutable
            // and we need to assign the next unique id
            Order newOrder = new Order(nextId(), order.getEmail(), order.getAddress(), order.getPayment(), order.getPrice(), order.getProducts(), order.getFulfilled());
            orders.put(newOrder.getId(), newOrder);
            save(); // may throw an IOException
            return newOrder;
        }
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public Order updateOrder(Order order) throws IOException {
        synchronized (orders) {
            if (orders.containsKey(order.getId()) == false)
                return null; // order does not exist

                orders.put(order.getId(), order);
            save(); // may throw an IOException
            return order;
        }
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public boolean deleteOrder(int id) throws IOException {
        synchronized (orders) {
            if (orders.containsKey(id)) {
                orders.remove(id);
                deletedIDs.add(id);
                return save();
            } else
                return false;
        }
    }
}
