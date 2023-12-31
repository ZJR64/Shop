package com.estore.api.estoreapi.model;

import java.util.logging.Logger;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a Product entity
 * 
 * @author AADITH CHARUGUNDLA
 */
public class User {
    private static final Logger LOG = Logger.getLogger(User.class.getName());

    // Package private for tests
    public static final String STRING_FORMAT = "Product [int=%d, email=%s, name=%s, password=%s, address=%s, admin=%b, payInfo=%s, cart=%s]";

    @JsonProperty("id")
    private int id;
    @JsonProperty("email")
    private String email;
    @JsonProperty("name")
    private String name;
    @JsonProperty("password")
    private String password;
    @JsonProperty("address")
    private String address;
    @JsonProperty("admin")
    private Boolean admin;
    @JsonProperty("payInfo")
    private String[] payInfo;
    @JsonProperty("cart")
    private Map<String, double[]> cart;

    /**
     * Create a user with the given email, name, password, address, admin, payInfo,
     * and cart
     * 
     * @param email    The email of the User
     * @param name     The name of the User
     * @param password The password of the User
     * @param address  The address of the User
     * @param admin    Whether or not the User is an admin
     * @param payInfo  An array containing credit card info
     * @param cart     A map which contains the user's shopping cart
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
    public User(@JsonProperty("id") int id, @JsonProperty("email") String email, @JsonProperty("name") String name,
            @JsonProperty("password") String password, @JsonProperty("address") String address,
            @JsonProperty("admin") Boolean admin, @JsonProperty("payInfo") String[] payInfo,
            @JsonProperty("cart") Map<String, double[]> cart) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.address = address;
        this.admin = admin;
        this.payInfo = payInfo;
        this.cart = cart;
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
     * Retrieves the email of the user
     * 
     * @return The email of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Retrieves the name of the user
     * 
     * @return The name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the user - necessary for JSON object to Java object
     * deserialization
     * 
     * @param name The name of the user
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the password of the user
     * 
     * @return The password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user - necessary for JSON object to Java object
     * deserialization
     * 
     * @param password The password of the user
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Sets the address of the user - necessary for JSON object to Java object
     * deserialization
     * 
     * @param address The address of the user
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Retrieves the address of the user
     * 
     * @return The address of the user
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the admin of the user - necessary for JSON object to Java object
     * deserialization
     * 
     * @param admin Boolean representing whether the User is an Admin
     */
    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    /**
     * Retrieves the admin of the user
     * 
     * @return Whether the user is an Admin
     */
    public Boolean getAdmin() {
        return admin;
    }

    /**
     * Sets the payInfo of the user - necessary for JSON object to Java
     * object deserialization
     * 
     * @param payInfo The payInfo of the user
     */
    public void setPayInfo(String[] payInfo) {
        this.payInfo = payInfo;
    }

    /**
     * Retrieves the payInfo of the user
     * 
     * @return The payInfo of the user
     */
    public String[] getPayInfo() {
        return payInfo;
    }

    /**
     * Retrieves the user's shopping cart
     * 
     * @return user's shopping cart
     */
    public Map<String, double[]> getCart() {
        return cart;
    }

    /**
     * Sets the cart of the user - necessary for JSON object to Java
     * object deserialization
     * 
     * @param cart new cart to set user's cart to
     */
    public void setCart(Map<String, double[]> cart) {
        this.cart = cart;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT, id, email, name, password, address, admin, payInfo, cart);
    }
}