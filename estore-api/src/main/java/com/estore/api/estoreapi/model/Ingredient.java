package com.estore.api.estoreapi.model;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents an Ingredient entity
 * 
 * @author Zak Rutherford
 */
public class Ingredient {
    private static final Logger LOG = Logger.getLogger(Ingredient.class.getName());

    // Package private for tests
    public static final String STRING_FORMAT = "Ingredient [id=%d, name=%s, type=%s, price=%f, volume=%f]";

    @JsonProperty("id")
    private int id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("type")
    private String type;
    @JsonProperty("price")
    private double price;
    @JsonProperty("volume")
    private double volume;

    /**
     * Create a ingredient with the given id, name, type, price, and volume
     * 
     * @param id     The id of the ingredient
     * @param name   The name of the ingredient
     * @param type   The type of the ingredient
     * @param price  The price of one ounce of the ingredient
     * @param volume The volume in ounces of the ingredient
     * 
     *               {@literal @}JsonProperty is used in serialization and
     *               deserialization
     *               of the JSON object to the Java object in mapping the fields. If
     *               a field
     *               is not provided in the JSON object, the Java field gets the
     *               default Java
     *               value, i.e. 0 for int
     */
    public Ingredient(@JsonProperty("id") int id, @JsonProperty("name") String name, @JsonProperty("type") String type,
            @JsonProperty("price") double price, @JsonProperty("volume") double volume) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.price = price;
        this.volume = volume;
    }

    /**
     * Retrieves the id of the ingredient
     * 
     * @return The id of the ingredient
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the name of the ingredient - necessary for JSON object to Java object
     * deserialization
     * 
     * @param name The name of the ingredient
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the name of the ingredient
     * 
     * @return The name of the ingredient
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the type of the ingredient - necessary for JSON object to Java object
     * deserialization
     * 
     * @param type The type of the ingredi ent
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Retrieves the type of the ingredient
     * 
     * @return The type of the ingredient
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the price of the ingredient - necessary for JSON object to Java object
     * deserialization
     * 
     * @param price The price of the ingredient
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Retrieves the price of the ingredient
     * 
     * @return The price of the ingredient
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the volume of the ingredient - necessary for JSON object to Java object
     * deserialization
     * 
     * @param volume The volume of the ingredient
     */
    public void setVolume(double volume) {
        this.volume = volume;
    }

    /**
     * Retrieves the volume of the ingredient
     * 
     * @return The volume of the ingredient
     */
    public double getVolume() {
        return volume;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT, id, name, type, price, volume);
    }
}