package com.estore.api.estoreapi.model;

import java.util.logging.Logger;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a Product entity
 * 
 * @author AADITH CHARUGUNDLA
 * @author Zak Rutherford
 */
public class Product {
    private static final Logger LOG = Logger.getLogger(Product.class.getName());

    // Package private for tests
    static final String STRING_FORMAT = "Product [id=%d, name=%s, type=%s, modPrice=%f, ingredients=%s]";

    @JsonProperty("id")
    private int id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("type")
    private String type;
    @JsonProperty("modPrice")
    private double modPrice;
    @JsonProperty("ingredients")
    private Map<String, Double> ingredients;

    /**
     * Create a product with the given id and name
     * 
     * @param id       The id of the product
     * @param name     The name of the product
     * @param type     The type of the product
     * @param modPrice The markup or sale for the product. Negative for sale, positive for markup.
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
            @JsonProperty("modPrice") double modPrice, @JsonProperty("ingredients") Map<String, Double> ingredients) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.modPrice = modPrice;
        this.ingredients = ingredients;
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
     * Sets the type of the product - necessary for JSON object to Java object
     * deserialization
     * 
     * @param type The type of the product
     */
    public void setType(String type) {
        this.type = type;
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
     * Sets the modPrice of the product - necessary for JSON object to Java object
     * deserialization
     * 
     * @param modPrice The modified price of the product
     */
    public void setModPrice(double modPrice) {
        this.modPrice = modPrice;
    }

    /**
     * Retrieves the modPrice of the product
     * 
     * @return The modified price of the product
     */
    public double getModPrice() {
        return modPrice;
    }

    /**
     * Sets the ingredients of the product - necessary for JSON object to Java object
     * deserialization
     * 
     * @param ingredients The ingredients of the product
     */
    public void setIngredients(Map<String, Double> ingredients) {
        this.ingredients = ingredients;
    }

    /**
     * Retrieves the ingredients of the product
     * 
     * @return The ingredients of the product
     */
    public Map<String, Double> getIngredients() {
        return ingredients;
    }





    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT, id, name, type, modPrice, ingredients);
    }
}