package com.estore.api.estoreapi.model;

import java.util.*;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents an Order entity
 * 
 * @author Zak Rutherford
 */
public class Order {
    private static final Logger LOG = Logger.getLogger(Order.class.getName());

    // Package private for tests
    static final String STRING_FORMAT = "Order [id=%d, email=%s, address=%s, payment=%s, price=%f, products=%s, fulfilled=%b]";
    /** this is only needed because String_format is private and the test classes are located outside the package
     * @return - the string format
     */
    public static String getSTRING_FORMAT(){return STRING_FORMAT;}


    @JsonProperty("id")
    private int id;
    @JsonProperty("email")
    private String email;
    @JsonProperty("address")
    private String address;
    @JsonProperty("payment")
    private String payment;
    @JsonProperty("price")
    private double price;
    @JsonProperty("products")
    private Map<String, Double[]> products;
    @JsonProperty("fulfilled")
    private boolean fulfilled;

    /**
     * Create a order with the given id, address, payment, price, products, and if fulfilled
     * 
     * @param id            The id of the order
     * @param email         The email of the user who made order
     * @param address       The address for the order
     * @param payment       The payment for the order
     * @param price         The price of the order
     * @param products      The products and their sizes
     * @param fulfilled    If the order has been fulfilled or not
     * 
     *               {@literal @}JsonProperty is used in serialization and
     *               deserialization
     *               of the JSON object to the Java object in mapping the fields. If
     *               a field
     *               is not provided in the JSON object, the Java field gets the
     *               default Java
     *               value, i.e. 0 for int
     */
    public Order(@JsonProperty("id") int id, @JsonProperty("email") String email, @JsonProperty("address") String address, @JsonProperty("payment") String payment,
    @JsonProperty("price") double price, @JsonProperty("products") Map<String, Double[]> products, @JsonProperty("fulfilled") boolean fulfilled) {
        this.id = id;
        this.email = email;
        this.address = address;
        this.payment = payment;
        this.price = price;
        this.products = products;
        this.fulfilled = fulfilled;
    }

    /**
     * Retrieves the id of the order
     * 
     * @return The id of the order
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the email of the order - necessary for JSON object to Java object
     * deserialization
     * 
     * @param email The email of the order
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retrieves the email of the order
     * 
     * @return The email of the order
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the address of the order - necessary for JSON object to Java object
     * deserialization
     * 
     * @param address The address of the order
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Retrieves the address of the order
     * 
     * @return The address of the order
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the payment of the order - necessary for JSON object to Java object
     * deserialization
     * 
     * @param payment The payment of the order
     */
    public void setPayment(String payment) {
        this.payment = payment;
    }

    /**
     * Retrieves the payment of the order
     * 
     * @return The payment of the order
     */
    public String getPayment() {
        return payment;
    }


    /**
     * Sets the price of the order - necessary for JSON object to Java object
     * deserialization
     * 
     * @param price The price of the order
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Retrieves the price of the order
     * 
     * @return The price of the order
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the products of the order - necessary for JSON object to Java object
     * deserialization
     * 
     * @param products The products of the order
     */
    public void setProducts(Map<String, Double[]> products) {
        this.products = products;
    }

    /**
     * Retrieves the products of the order
     * 
     * @return The products of the order
     */
    public Map<String, Double[]> getProducts() {
        return products;
    }

    /**
     * Sets the fulfilled status of the order - necessary for JSON object to Java object
     * deserialization
     * 
     * @param fulfilled Boolean representing whether the order is fulfilled
     */
    public void setFulfilled(Boolean fulfilled) {
        this.fulfilled = fulfilled;
    }

    /**
     * Retrieves the fulfilled status of the user
     * 
     * @return Whether the order is fulfilled
     */
    public Boolean getFulfilled() {
        return fulfilled;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT, id, email, address, payment, price, products, fulfilled);
    }
}