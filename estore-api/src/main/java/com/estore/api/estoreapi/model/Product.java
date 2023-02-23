package com.estore.api.estoreapi.model;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a Product entity
 * 
 * @author AADITH CHARUGUNDLA
 */
public class Product {
    private static final Logger LOG = Logger.getLogger(Product.class.getName());

    // Package private for tests
    static final String STRING_FORMAT = "Product [id=%d, name=%s, type=%s, price=%f, quantity=%d]";

    @JsonProperty("id")
    private int id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("type")
    private String type;
    @JsonProperty("price")
    private float price;
    @JsonProperty("quantity")
    private int quantity;

    /**
     * Create a product with the given id and name
     * 
     * @param id       The id of the product
     * @param name     The name of the product
     * @param type     The type of the product
     * @param price    The price of the product
     * @param quantity the quantity of the product
     * 
     *                 {@literal @}JsonProperty is used in serialization and
     *                 deserialization
     *                 of the JSON object to the Java object in mapping the fields.
     *                 If a
     *                 field
     *                 is not provided in the JSON object, the Java field gets the
     *                 default Java
     *                 value, i.e. 0 for int
     */
    public Product(@JsonProperty("id") int id, @JsonProperty("name") String name, @JsonProperty("type") String type,
            @JsonProperty("price") float price, @JsonProperty("quantity") int quantity) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.price = price;
        this.quantity = quantity;
    }

    /**
     * Retrieves the id of the product
     * 
     * @return The id of the product
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the name of the product - necessary for JSON object to Java object
     * deserialization
     * 
     * @param name The name of the product
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the name of the product
     * 
     * @return The name of the product
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves the type of the product
     * 
     * @return The type of the product
     */
    public String getType() {
        return type;
    }

    /**
     * Retrieves the price of the product
     * 
     * @return The type of the product
     */
    public float getPrice() {
        return price;
    }

    /**
     * Retrieves the quantity of the product
     * 
     * @return The type of the product
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT, id, name, type,price,quantity);
    }
}