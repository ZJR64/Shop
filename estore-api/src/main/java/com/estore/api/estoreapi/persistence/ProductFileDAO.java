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

import com.estore.api.estoreapi.model.Product;

/**
 * Implements the functionality for JSON file-based peristance for Products
 * 
 * {@literal @}Component Spring annotation instantiates a single instance of
 * this
 * class and injects the instance into other classes as needed
 * 
 * @author AADITH CHARUGUNDLA
 */
@Component
public class ProductFileDAO implements ProductDAO {
    private static final Logger LOG = Logger.getLogger(ProductFileDAO.class.getName());
    public Map<Integer, Product> products; // Provides a local cache of the product objects
                                    // so that we don't need to read from the file
                                    // each time
    private ObjectMapper objectMapper; // Provides conversion between Product
                                       // objects and JSON text format written
                                       // to the file
    private static int currentID; // The lates ID to be assigned to a product
    private static ArrayList<Integer> deletedIDs; // arraylist of all deleted ids
    private String filename; // Filename to read from and write to

    /**
     * Creates a Product File Data Access Object
     * 
     * @param filename     Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization
     *                     and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public ProductFileDAO(@Value("${products.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load(); // load the products from the file
    }

    /**
     * Generates the next id for a new {@linkplain Product product}
     * 
     * @return The next id
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
     * Generates an array of {@linkplain Product products} from the tree map
     * 
     * @return The array of {@link Product products}, may be empty
     */
    private Product[] getProductsArray() {
        return getProductsArray(null);
    }

    /**
     * Generates an array of {@linkplain Product products} from the tree map for any
     * {@linkplain Product products} that contains the text specified by
     * containsText
     * <br>
     * If containsText is null, the array contains all of the {@linkplain Product
     * products}
     * in the tree map
     * 
     * @return The array of {@link Product products}, may be empty
     */
    private Product[] getProductsArray(String containsText) { // if containsText == null, no filter
        ArrayList<Product> productArrayList = new ArrayList<>();

        for (Product product : products.values()) {
            if (containsText == null || product.getName().contains(containsText)) {
                productArrayList.add(product);
            }
        }

        Product[] productArray = new Product[productArrayList.size()];
        productArrayList.toArray(productArray);
        return productArray;
    }

    /**
     * Saves the {@linkplain Product products} from the map into the file as an
     * array of JSON objects
     * 
     * @return true if the {@link Product products} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        Product[] productArray = getProductsArray();

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename), productArray);
        return true;
    }

    /**
     * Loads {@linkplain Product products} from the JSON file into the map
     * <br>
     * Also sets next id to one more than the greatest id found in the file
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        products = new TreeMap<>();
        currentID = 0;
        deletedIDs = new ArrayList<Integer>();


        // Deserializes the JSON objects from the file into an array of products
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        Product[] productArray = objectMapper.readValue(new File(filename), Product[].class);

        // Add each product to the tree map and keep track of the greatest id
        int maxID = 0;
        for (Product product : productArray) {
            products.put(product.getId(), product);
            if (product.getId() > maxID)
                maxID = product.getId();
        }

        //assign the value.
        currentID = maxID;

        //find the missing ids to fil in the deleted id list
        for (int i = 1; i <= maxID; i++) {
            deletedIDs.add(i);
        }

        for (Product product : productArray) {
            deletedIDs.remove(deletedIDs.indexOf(product.getId()));
        }

        return true;
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public Product[] getProducts() {
        synchronized (products) {
            return getProductsArray();
        }
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public Product[] findProducts(String containsText) {
        synchronized (products) {
            String searchLowerCase = containsText.toLowerCase();
            List<Product> matches = new ArrayList<>();
            for (Product product : getProductsArray()) {
                if (product.getName().toLowerCase().contains(searchLowerCase)) {
                    matches.add(product);
                }
            }
            return matches.toArray(new Product[0]);
        }
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public Product getProduct(int id) {
        synchronized (products) {
            if (products.containsKey(id))
                return products.get(id);
            else
                return null;
        }
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public Product createProduct(Product product) throws IOException {
        synchronized (products) {
            // We create a new product object because the id field is immutable
            // and we need to assign the next unique id
            Product newProduct = new Product(nextId(), product.getName(), product.getType(), product.getModPrice(),
                    product.getIngredients());
            products.put(newProduct.getId(), newProduct);
            save(); // may throw an IOException
            return newProduct;
        }
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public Product updateProduct(Product product) throws IOException {
        synchronized (products) {
            if (products.containsKey(product.getId()) == false)
                return null; // product does not exist

            products.put(product.getId(), product);
            save(); // may throw an IOException
            return product;
        }
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public boolean deleteProduct(int id) throws IOException {
        synchronized (products) {
            if (products.containsKey(id)) {
                products.remove(id);
                deletedIDs.add(id);
                return save();
            } else
                return false;
        }
    }
}
