package com.estore.api.estoreapi;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.estore.api.estoreapi.model.User;
import com.estore.api.estoreapi.persistence.UserFileDAO;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
    * The unit test suite for the UserDAO interface class
    * This may or may not work
    * @author FRANK ANDES (ffa7120)
*/

@Tag("Persistence-tier")
@SpringBootTest
@TestPropertySource(locations="classpath:application-test.properties")
public class EstoreApiUserFileDAOTest {

    @Autowired
    private UserFileDAO userFileDAO;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${users.file}")
    private String usersFilename;

    @BeforeEach
    void setUp() throws IOException {
        // Delete the users file before each test
        assertTrue(new File(usersFilename).delete());
    }

    @Test
    void testAddUser() throws IOException {
        User user = new User(0, "JohnDoe@example.com", "John Doe", "password123", "123 Main Street SW", null, null);
        userFileDAO.createUser(user);
        User[] users = userFileDAO.getUsers();
        assertEquals(1, users.length);
        assertEquals(user, users[0]);
    }

    @Test
    void testAddUserWithEmptyName() {
        User user = new User(0, "JaneDoe@example.com", "Jane doe", "password123", "123 Main Street SW", null, null);
        assertThrows(IllegalArgumentException.class, () -> userFileDAO.createUser(user));
    }


    @Test
    void testUpdateUser() throws IOException {
        User user = new User(0, "JohnDoe@example.com", "John Doe", "password123", "123 Main Street SW", null, null);
        userFileDAO.createUser(user);
        User updatedUser = new User(user.getId(), "JohnDoe@example.com", "Jane Doe", "otherpassword", "123 Main Street SW", null, null);
        userFileDAO.updateUser(updatedUser);
        User[] users = userFileDAO.getUsers();
        assertEquals(1, users.length);
        assertEquals(updatedUser, users[0]);
    }

    @Test
    void testDeleteUser() throws IOException {
        User user = new User(0, "JohnDoe@example.com", "John Doe", "password123", "123 Main Street SW", null, null);
        userFileDAO.createUser(user);
        userFileDAO.deleteUser(user.getId());
        User[] users = userFileDAO.getUsers();
        assertEquals(0, users.length);
    }

    @Test
    void testGetUsers() throws IOException {
        User user1 = new User(0, "JohnDoe@example.com", "John Doe", "password123", "123 Main Street SW", null, null);
        User user2 = new User(1, "JaneDoe@example.com", "Jane doe", "password123", "123 Main Street SW", null, null);
        userFileDAO.createUser(user1);
        userFileDAO.createUser(user2);
        User[] users = userFileDAO.getUsers();
        assertEquals(2, users.length);
        assertTrue(Arrays.asList(users).contains(user1));
        assertTrue(Arrays.asList(users).contains(user2));
    }

    @Test
    void testGetUsersWithFilter() throws IOException {
        User user1 = new User(0, "JohnDoe@example.com", "John Doe", "password123", "123 Main Street SW", null, null);
        User user2 = new User(1, "JaneDoe@example.com", "Jane doe", "password123", "123 Main Street SW", null, null);
        userFileDAO.createUser(user1);
        userFileDAO.createUser(user2);
        User[] users = userFileDAO.findUsers("Jane Doe");
        assertEquals(1, users.length);
        assertTrue(Arrays.asList(users).contains(user2));
        assertFalse(Arrays.asList(users).contains(user1));
    }
}