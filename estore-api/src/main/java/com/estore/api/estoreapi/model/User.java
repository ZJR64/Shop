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
public class User {
    private static final Logger LOG = Logger.getLogger(User.class.getName());

    // Package private for tests
    static final String STRING_FORMAT = "Product [email=%s, password=%s, address=%s, admin=%b, payInfo=%s]";

    @JsonProperty("email")
    private String email;
    @JsonProperty("password")
    private String password;
    @JsonProperty("address")
    private String address;
    @JsonProperty("admin")
    private Boolean admin;
    @JsonProperty("payInfo")
    private String[] payInfo;

    /**
     * Create a user with the given email, password, address, admin, and payInfo
     * 
     * @param email    The email of the User
     * @param password The password of the User
     * @param address  The address of the User
     * @param admin    Whether or not the User is an admin
     * @param payInfo  An array containing credit card info
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
    public User(@JsonProperty("email") String email, @JsonProperty("password") String password,
            @JsonProperty("address") String address,
            @JsonProperty("Admin") Boolean admin, @JsonProperty("payInfo") String[] payInfo) {
        this.email = email;
        this.password = password;
        this.address = address;
        this.admin = admin;
        this.payInfo = payInfo;
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
     * object
     * deserialization
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
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT, email, password, address, admin, payInfo);
    }
}