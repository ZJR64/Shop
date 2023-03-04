// package com.estore.api.estoreapi;

// import org.junit.jupiter.api.Tag;
// import org.junit.jupiter.api.Test;
// import org.springframework.boot.test.context.SpringBootTest;
// import static org.junit.Assert.*;
// import java.io.IOException;
// import org.junit.Test;

// /**
//     * The unit test suite for the UserDAO interface class
//     * This may or may not work
//     * @author FRANK ANDES (ffa7120)
// */

// @Tag("Persistence-tier")
// @SpringBootTest
// public class EstoreApiUserDAOTest {

//     private UserDAO dao = new UserDAO();

//     @Test
//     public void testGetUsers() throws IOException {
//         User[] users = dao.getUsers();
//         assertNotNull(users);
//     }

//     @Test
//     public void testFindUsers() throws IOException {
//         User[] users = dao.findUsers("John");
//         assertNotNull(users);
//     }

//     @Test
//     public void testGetUser() throws IOException {
//         User user = dao.getUser(1);
//         assertNotNull(user);
//     }

//     @Test
//     public void testCreateUser() throws IOException {
//         User user = new User();
//         user.setName("Jane Doe");
//         user.setEmail("jane.doe@example.com");
//         user.setPassword("password");
//         User createdUser = dao.createUser(user);
//         assertNotNull(createdUser);
//         assertNotNull(createdUser.getEmail());
//         assertNotEquals("jane.doe@example.com", createdUser.getEmail());
//     }

//     @Test
//     public void testUpdateUser() throws IOException {
//         User user = dao.getUser(1);
//         assertNotNull(user);
//         String newName = "John Doe";
//         user.setName(newName);
//         User updatedUser = dao.updateUser(user);
//         assertNotNull(updatedUser);
//         assertEquals(newName, updatedUser.getName());
//     }

//     @Test
//     public void testDeleteUser() throws IOException {
//         boolean deleted = dao.deleteUser(1);
//         assertTrue(deleted);
//     }

// }
