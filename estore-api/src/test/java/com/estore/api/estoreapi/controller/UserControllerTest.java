package com.estore.api.estoreapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Map;

import com.estore.api.estoreapi.persistence.UserDAO;
import com.estore.api.estoreapi.model.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Test the User Controller class
 * 
 * @author Zak Rutherfored
 */
@Tag("Controller-tier")
public class UserControllerTest {
    private UserController userController;
    private UserDAO mockUserDAO;

    /**
     * Before each test, create a new UserController object and inject
     * a mock User DAO
     */
    @BeforeEach
    public void setupUserController() {
        mockUserDAO = mock(UserDAO.class);
        userController = new UserController(mockUserDAO);
    }

    @Test
    public void testGetUser() throws IOException { // getUser may throw IOException
        // Setup
        String[] test = new String[1];
        test[0] = "123456789";
        double[] temp = new double[] {10.0, 27.0 };
        Map<String, double[]> cart = Map.of("Test Blend", temp);
        User user = new User(99, "test@gmail.com", "Jim Bean", "12345", "123 Idiot Street", true, test, cart);
        // When the same id is passed in, our mock User DAO will return the User object
        when(mockUserDAO.getUser(user.getId())).thenReturn(user);

        // Invoke
        ResponseEntity<User> response = userController.getUser(user.getId());

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    public void testGetUserNotFound() throws Exception { // createUser may throw IOException
        // Setup
        int userId = 99;
        // When the same id is passed in, our mock User DAO will return null, simulating
        // no user found
        when(mockUserDAO.getUser(userId)).thenReturn(null);

        // Invoke
        ResponseEntity<User> response = userController.getUser(userId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetUserHandleException() throws Exception { // createUser may throw IOException
        // Setup
        int userId = 99;
        // When getUser is called on the Mock User DAO, throw an IOException
        doThrow(new IOException()).when(mockUserDAO).getUser(userId);

        // Invoke
        ResponseEntity<User> response = userController.getUser(userId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testGetUserFromEmail() throws IOException { // getUser may throw IOException
        // Setup
        String[] test = new String[1];
        test[0] = "123456789";
        double[] temp = new double[] {10.0, 27.0 };
        Map<String, double[]> cart = Map.of("Test Blend", temp);
        User user = new User(99, "test@gmail.com", "Jim Bean", "12345", "123 Idiot Street", true, test, cart);
        // When the same id is passed in, our mock User DAO will return the User object
        when(mockUserDAO.getUser(user.getEmail())).thenReturn(user);

        // Invoke
        ResponseEntity<User> response = userController.getUser(user.getEmail());

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    public void testGetUserNotFoundFromEmail() throws Exception { // createUser may throw IOException
        // Setup
        String email = "email@not.found";
        // When the same id is passed in, our mock User DAO will return null, simulating
        // no user found
        when(mockUserDAO.getUser(email)).thenReturn(null);

        // Invoke
        ResponseEntity<User> response = userController.getUser(email);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetUserHandleExceptionFromEmail() throws Exception { // createUser may throw IOException
        // Setup
        String email = "email@not.found";
        // When getUser is called on the Mock User DAO, throw an IOException
        doThrow(new IOException()).when(mockUserDAO).getUser(email);

        // Invoke
        ResponseEntity<User> response = userController.getUser(email);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testCreateUser() throws IOException { // createUser may throw IOException
        // Setup
        String[] test = new String[1];
        test[0] = "123456789";
        double[] temp = new double[] {10.0, 27.0 };
        Map<String, double[]> cart = Map.of("Test Blend", temp);
        User user = new User(99, "test@gmail.com", "Jim Bean", "12345", "123 Idiot Street", true, test, cart);
        // when createUser is called, return true simulating successful
        // creation and save
        when(mockUserDAO.createUser(user)).thenReturn(user);

        // Invoke
        ResponseEntity<User> response = userController.createUser(user);

        // Analyze
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    public void testCreateUserFailed() throws IOException { // createUser may throw IOException
        // Setup
        String[] test = new String[1];
        test[0] = "123456789";
        double[] temp = new double[] {10.0, 27.0 };
        Map<String, double[]> cart = Map.of("Test Blend", temp);
        User user = new User(99, "test@gmail.com", "Jim Bean", "12345", "123 Idiot Street", true, test, cart);
        // when createUser is called, return false simulating failed
        // creation and save
        when(mockUserDAO.createUser(user)).thenReturn(null);

        // Invoke
        ResponseEntity<User> response = userController.createUser(user);

        // Analyze
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void testCreateUserHandleException() throws IOException { // createUser may throw IOException
        // Setup
        String[] test = new String[1];
        test[0] = "123456789";
        double[] temp = new double[] {10.0, 27.0 };
        Map<String, double[]> cart = Map.of("Test Blend", temp);
        User user = new User(99, "test@gmail.com", "Jim Bean", "12345", "123 Idiot Street", true, test, cart);

        // When createUser is called on the Mock User DAO, throw an IOException
        doThrow(new IOException()).when(mockUserDAO).createUser(user);

        // Invoke
        ResponseEntity<User> response = userController.createUser(user);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testUpdateUser() throws IOException { // updateUser may throw IOException
        // Setup
        String[] test = new String[2];
        test[0] = "123456789";
        test[1] = "987654321";
        double[] temp = new double[] {10.0, 27.0 };
        Map<String, double[]> cart = Map.of("Test Blend", temp);
        User user = new User(99, "dumbdumb@gmail.com", "Me", "AAAHHH", "567 send me to heaven", false, test, cart);
        // when updateUser is called, return true simulating successful
        // update and save
        when(mockUserDAO.updateUser(user)).thenReturn(user);
        ResponseEntity<User> response = userController.updateUser(user);
        user.setName("Bolt");

        // Invoke
        response = userController.updateUser(user);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    public void testUpdateUserFailed() throws IOException { // updateUser may throw IOException
        // Setup
        String[] test = new String[2];
        test[0] = "123456789";
        test[1] = "987654321";
        double[] temp = new double[] {10.0, 27.0 };
        Map<String, double[]> cart = Map.of("Test Blend", temp);
        User user = new User(99, "dumbdumb@gmail.com", "Me", "AAAHHH", "567 send me to heaven", false, test, cart);
        // when updateUser is called, return true simulating successful
        // update and save
        when(mockUserDAO.updateUser(user)).thenReturn(null);

        // Invoke
        ResponseEntity<User> response = userController.updateUser(user);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testUpdateUserHandleException() throws IOException { // updateUser may throw IOException
        // Setup
        String[] test = new String[2];
        test[0] = "123456789";
        test[1] = "987654321";
        double[] temp = new double[] {10.0, 27.0 };
        Map<String, double[]> cart = Map.of("Test Blend", temp);
        User user = new User(99, "dumbdumb@gmail.com", "Me", "AAAHHH", "567 send me to heaven", false, test, cart);
        // When updateUser is called on the Mock User DAO, throw an IOException
        doThrow(new IOException()).when(mockUserDAO).updateUser(user);

        // Invoke
        ResponseEntity<User> response = userController.updateUser(user);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testGetUsers() throws IOException { // getUsers may throw IOException
        // Setup
        User[] users = new User[2];
        String[] test1 = new String[2];
        test1[0] = "123456789";
        test1[1] = "987654321";
        String[] test2 = new String[2];
        test2[0] = "9183487164";
        test2[1] = "0495761495";
        double[] temp = new double[] {10.0, 27.0 };
        Map<String, double[]> cart = Map.of("Test Blend", temp);
        double[] temp2 = new double[] {10.0, 27.0 };
        Map<String, double[]> cart2 = Map.of("Test Blend", temp2);
        users[0] = new User(99, "dumbdumb@gmail.com", "Me", "AAAHHH", "567 send me to heaven", false, test1, cart);
        users[1] = new User(100, "ohgod@yahoo.com", "NoThanks", "crap", "oops road", true, test2, cart2);
        // When getUsers is called return the users created above
        when(mockUserDAO.getUsers()).thenReturn(users);

        // Invoke
        ResponseEntity<User[]> response = userController.getUsers();

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(users, response.getBody());
    }

    @Test
    public void testGetUsersHandleException() throws IOException { // getUsers may throw IOException
        // Setup
        // When getUsers is called on the Mock User DAO, throw an IOException
        doThrow(new IOException()).when(mockUserDAO).getUsers();

        // Invoke
        ResponseEntity<User[]> response = userController.getUsers();

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testSearchUsers() throws IOException { // findUsers may throw IOException
        // Setup
        String searchString = "el";
        User[] users = new User[2];
        String[] test1 = new String[2];
        test1[0] = "123456789";
        test1[1] = "987654321";
        String[] test2 = new String[2];
        test2[0] = "9183487164";
        test2[1] = "0495761495";
        double[] temp = new double[] {10.0, 27.0 };
        Map<String, double[]> cart = Map.of("Test Blend", temp);
        double[] temp2 = new double[] {10.0, 27.0 };
        Map<String, double[]> cart2 = Map.of("Test Blend", temp2);
        users[0] = new User(99, "dumbdumb@gmail.com", "Melvin", "AAAHHH", "567 send me to heaven", false, test1, cart);
        users[1] = new User(100, "ohgod@yahoo.com", "Kelvin", "crap", "oops road", true, test2, cart2);
        // When findUsers is called with the search string, return the two
        /// users above
        when(mockUserDAO.findUsers(searchString)).thenReturn(users);

        // Invoke
        ResponseEntity<User[]> response = userController.searchUsers(searchString);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(users, response.getBody());
    }

    @Test
    public void testSearchUsersFailed() throws IOException { // findUsers may throw IOException
        // Setup
        // Setup
        String searchString = "ko";
        User[] users = null;
        // when updateUser is called, return true simulating successful
        // update and save
        /// users above
        when(mockUserDAO.findUsers(searchString)).thenReturn(users);

        // Invoke
        ResponseEntity<User[]> response = userController.searchUsers(searchString);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testSearchUsersHandleException() throws IOException { // findUsers may throw IOException
        // Setup
        String searchString = "an";
        // When createUser is called on the Mock User DAO, throw an IOException
        doThrow(new IOException()).when(mockUserDAO).findUsers(searchString);

        // Invoke
        ResponseEntity<User[]> response = userController.searchUsers(searchString);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testDeleteUser() throws IOException { // deleteUser may throw IOException
        // Setup
        int userId = 99;
        // when deleteUser is called return true, simulating successful deletion
        when(mockUserDAO.deleteUser(userId)).thenReturn(true);

        // Invoke
        ResponseEntity<User> response = userController.deleteUser(userId);

        // Analyze
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeleteUserNotFound() throws IOException { // deleteUser may throw IOException
        // Setup
        int userId = 99;
        // when deleteUser is called return false, simulating failed deletion
        when(mockUserDAO.deleteUser(userId)).thenReturn(false);

        // Invoke
        ResponseEntity<User> response = userController.deleteUser(userId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeleteUserHandleException() throws IOException { // deleteUser may throw IOException
        // Setup
        int userId = 99;
        // When deleteUser is called on the Mock User DAO, throw an IOException
        doThrow(new IOException()).when(mockUserDAO).deleteUser(userId);

        // Invoke
        ResponseEntity<User> response = userController.deleteUser(userId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
