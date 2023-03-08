package com.estore.api.estoreapi.Persistence;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import com.estore.api.estoreapi.model.User;
import com.estore.api.estoreapi.persistence.UserFileDAO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Test the User File DAO class
 * 
 * @author SWEN Faculty
 */
@Tag("Persistence-tier")
public class EstoreApiUserFileDAOTest {
    UserFileDAO userFileDAO;
    User[] testUsers;
    ObjectMapper mockObjectMapper;

    /**
     * Before each test, we will create and inject a Mock Object Mapper to
     * isolate the tests from the underlying file
     * @throws IOException
     */
    @BeforeEach
    public void setupUserFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testUsers = new User[3];
        String[] pay = {"ROLE_USER"};
        testUsers[0] = new User(99,"JaneDoe@example.com", "Jane Doe", "password123", "123 Main Street", false, pay);
        testUsers[1] = new User(100,"JohnDoe@example.com", "John Doe", "password123", "1234 Main Street", false, pay);
        testUsers[2] = new User(101,"Booty@example.com", "Ja Booty", "password123", "123 Jabooty Street", false, pay);

        // When the object mapper is supposed to read from the file
        // the mock object mapper will return the user array above
        when(mockObjectMapper
            .readValue(new File("doesnt_matter.txt"),User[].class))
                .thenReturn(testUsers);
            
                userFileDAO = new UserFileDAO("doesnt_matter.txt", mockObjectMapper);
        
    }

    @Test
    public void testGetUsers() {
        // Invoke
        User[] users = userFileDAO.getUsers();

        // Analyze
        assertEquals(users.length,testUsers.length);
        for (int i = 0; i < testUsers.length;++i)
            assertEquals(users[i],testUsers[i]);
    }

    @Test
    public void testFindUsers() {
        // Invoke
        User[] users = userFileDAO.findUsers("Doe");

        // Analyze
        assertEquals(users.length,2);
        assertEquals(users[0],testUsers[0]);
        assertEquals(users[1],testUsers[1]);
    }

    @Test
    public void testGetUser() {
        // Invoke
        User user = userFileDAO.getUser(99);

        // Analzye
        assertEquals(user,testUsers[0]);
    }

    @Test
    public void testGetUserByEmail() {
        // Invoke
        User user = userFileDAO.getUser("JaneDoe@example.com");

        // Analzye
        assertEquals(user,testUsers[0]);
    }

    @Test
    public void testDeleteUser() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> userFileDAO.deleteUser(99),
                            "Unexpected exception thrown");

        // Analzye
        assertEquals(result,true);
        // We check the internal tree map size against the length
        // of the test users array - 1 (because of the delete)
        // Because User attribute of UserFileDAO is package private
        // we can access it directly
        assertEquals(userFileDAO.users.size(),testUsers.length-1);
    }

    @Test
    public void testCreateUser() {
        // Setup
        // make array of strings
        String[] pay = {"ROLE_USER"};

        User user = new User(98,"Clap@example.com", "Ja Booty Clap", "password123", "123 Jabooty Street", false, pay);

        // Invoke
        User result = assertDoesNotThrow(() -> userFileDAO.createUser(user),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        User actual = user;
        assertEquals(actual.getId(),user.getId());
        assertEquals(actual.getName(),user.getName());
    }

    @Test
    public void testUpdateUser() {
        // Setup

        String[] pay = {"ROLE_USER"};
        User user = new User(99,"Za@example.com", "Za Clap", "password123", "123 Jabooty Street", false, pay);

        // Invoke
        User result = assertDoesNotThrow(() -> userFileDAO.updateUser(user),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        User actual = userFileDAO.getUser(user.getId());
        assertEquals(actual,user);
    }

    @Test
    public void testSaveException() throws IOException{
        String[] pay = {"ROLE_USER"};

        doThrow(new IOException())
            .when(mockObjectMapper)
                .writeValue(any(File.class),any(User[].class));

            
        User user = new User(102,"RAAAAAA@example.com", "Dino", "password123", "123 Jabooty Street", false, pay);

        assertThrows(IOException.class,
                        () -> userFileDAO.createUser(user),
                        "IOException not thrown");
    }

    @Test
    public void testGetUserNotFound() {
        // Invoke
        User user = userFileDAO.getUser(98);

        // Analyze
        assertEquals(user,null);
    }

    @Test
    public void testGetUserByEmailNotFound() {
        // Invoke
        User user = userFileDAO.getUser("fail");

        // Analzye
        assertEquals(user,null);
    }

    @Test
    public void testDeleteUserNotFound() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> userFileDAO.deleteUser(98),
                                                "Unexpected exception thrown");

        // Analyze
        assertEquals(result,false);
        assertEquals(userFileDAO.users.size(),testUsers.length);
    }

    @Test
    public void testUpdateUserNotFound() {
        // Setup

        String[] pay = {"ROLE_USER"};
        User user = new User(98,"RadioHead@example.com", "Saul Goodman", "password123", "123 Jabooty Street", false, pay);

        // Invoke
        User result = assertDoesNotThrow(() -> userFileDAO.updateUser(user), "Unexpected exception thrown");

        // Analyze
        assertNull(result);
    }

    @Test
    public void testConstructorException() throws IOException {
        // Setup
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        // We want to simulate with a Mock Object Mapper that an
        // exception was raised during JSON object deseerialization
        // into Java objects
        // When the Mock Object Mapper readValue method is called
        // from the UserFileDAO load method, an IOException is
        // raised
        doThrow(new IOException())
            .when(mockObjectMapper)
                .readValue(new File("doesnt_matter.txt"),User[].class);

        // Invoke & Analyze
        assertThrows(IOException.class,
                        () -> new UserFileDAO("doesnt_matter.txt",mockObjectMapper),
                        "IOException not thrown");
    }
}
