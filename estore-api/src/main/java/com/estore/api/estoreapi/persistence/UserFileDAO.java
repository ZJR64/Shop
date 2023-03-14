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

import com.estore.api.estoreapi.model.User;

/**
 * Implements the functionality for JSON file-based peristance for Users
 * 
 * {@literal @}Component Spring annotation instantiates a single instance of
 * this
 * class and injects the instance into other classes as needed
 * 
 * @author AADITH CHARUGUNDLA
 */
@Component
public class UserFileDAO implements UserDAO {
    private static final Logger LOG = Logger.getLogger(UserFileDAO.class.getName());
    public Map<Integer, User> users; // Provides a local cache of the User objects
                              // so that we don't need to read from the file
                              // each time
    private ObjectMapper objectMapper; // Provides conversion between Users
                                       // objects and JSON text format written
                                       // to the file
    private static int currentID; // The lates ID to be assigned to an user
    private static ArrayList<Integer> deletedIDs; // arraylist of all deleted ids
    private String filename; // Filename to read from and write to

    /**
     * Creates a User File Data Access Object
     * 
     * @param filename     Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization
     *                     and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public UserFileDAO(@Value("${users.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load(); // load the users from the file
    }

    /**
     * Generates the next id for a new {@linkplain User user}
     * 
     * @return The an id
     */
    private synchronized static int nextId() {
        if (deletedIDs.size() > 0) {
            return deletedIDs.remove(0);
        } else {
            currentID++;
            return currentID;
        }
    }

    /**
     * Generates an array of {@linkplain User users} from the tree map
     * 
     * @return The array of {@link User users}, may be empty
     */
    private User[] getUsersArray() {
        return getUsersArray(null);
    }

    /**
     * Generates an array of {@linkplain User users} from the tree map for any
     * {@linkplain User users} that contains the text specified by
     * containsText
     * <br>
     * If containsText is null, the array contains all of the {@linkplain User
     * users}
     * in the tree map
     * 
     * @return The array of {@link User users}, may be empty
     */
    private User[] getUsersArray(String containsText) { // if containsText == null, no filter
        ArrayList<User> userArrayList = new ArrayList<>();

        for (User user : users.values()) {
            if (containsText == null || user.getName().contains(containsText)) {
                userArrayList.add(user);
            }
        }

        User[] userArray = new User[userArrayList.size()];
        userArrayList.toArray(userArray);
        return userArray;
    }

    /**
     * Saves the {@linkplain User users} from the map into the file as an
     * array of JSON objects
     * 
     * @return true if the {@link User users} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        User[] userArray = getUsersArray();

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename), userArray);
        return true;
    }

    /**
     * Loads {@linkplain User users} from the JSON file into the map
     * <br>
     * Also sets next id to one more than the greatest id found in the file
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        users = new TreeMap<>();
        currentID = 0;
        deletedIDs = new ArrayList<Integer>();

        // Deserializes the JSON objects from the file into an array of users
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        User[] userArray = objectMapper.readValue(new File(filename), User[].class);

        // Add each user to the tree map and keep track of the greatest id
        int maxID = 0;
        for (User user : userArray) {
            users.put(user.getId(), user);
            if (user.getId() > maxID)
                maxID = user.getId();
        }

        // assign the value.
        currentID = maxID;

        // find the missing ids to fil in the deleted id list
        for (int i = 1; i <= maxID; i++) {
            deletedIDs.add(i);
        }

        for (User user : userArray) {
            deletedIDs.remove(deletedIDs.indexOf(user.getId()));
        }

        return true;
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public User[] getUsers() {
        synchronized (users) {
            return getUsersArray();
        }
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public User[] findUsers(String containsText) {
        synchronized (users) {
            String searchLowerCase = containsText.toLowerCase();
            List<User> matches = new ArrayList<>();
            for (User user : getUsersArray()) {
                if (user.getName().toLowerCase().contains(searchLowerCase)) {
                    matches.add(user);
                }
            }
            return matches.toArray(new User[0]);
        }
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public User getUser(int id) {
        synchronized (users) {
            if (users.containsKey(id))
                return users.get(id);
            else
                return null;
        }
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public User getUser(String email) {
        synchronized (users) {
            for (User user : getUsersArray()) {
                if (user.getEmail().equals(email)) {
                    return user;
                }
            }
            return null;
        }
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public User createUser(User user) throws IOException {
        synchronized (users) {
            // We create a new user object because the id field is immutable
            // and we need to assign the next unique id
            User newUser = new User(nextId(), user.getEmail(), user.getName(), user.getPassword(), user.getAddress(),
                    user.getAdmin(), user.getPayInfo());
            users.put(newUser.getId(), newUser);
            save(); // may throw an IOException
            return newUser;
        }
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public User updateUser(User user) throws IOException {
        synchronized (users) {
            if (users.containsKey(user.getId()) == false)
                return null; // user does not exist

            users.put(user.getId(), user);
            save(); // may throw an IOException
            return user;
        }
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public boolean deleteUser(int id) throws IOException {
        synchronized (users) {
            if (users.containsKey(id)) {
                users.remove(id);
                deletedIDs.add(id);
                return save();
            } else
                return false;
        }
    }
}
