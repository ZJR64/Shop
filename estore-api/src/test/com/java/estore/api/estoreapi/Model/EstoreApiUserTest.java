package com.estore.api.estoreapi.model.UserTest;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * The unit test suite for the User class
 * 
 * @author AADITH CHARUGUNDLA (APC3277)
 */
@Tag("Model-tier")
@SpringBootTest
class EstoreApiApplicationTests {

    @Test
    public void testCtor() {
        // Setup
        int expected_id = 69;
        String expected_email = "scammer@scam.com";
        String expected_name = "Deez Nuts";
        String expected_password = "password123";
        String expected_address = "129 balls lane";
        Boolean expected_admin = false;
        String[] expected_payInfo = new String[] { "123", "235", "1351135" };

        // Invoke
        User user = new User(expected_id, expected_email, expected_name, expected_password, expected_address,
                expected_admin, expected_payInfo);

        // Analyze
        assertEquals(expected_id, user.getId());
        assertEquals(expected_email, user.getEmail());
        assertEquals(expected_name, user.getName());
        assertEquals(expected_password, user.getType());
        assertEquals(expected_admin, user.getPrice());
        assertEquals(expected_payInfo, user.getVolume());

    }

    @Test
    public void testName() {
        // Setup
        int id = 69;
        String name = "Deez Nuts";
        String password = "Bean";
        float admin = 0.69;
        float payInfo = 420.69;
        Ingredient user = new Ingredient(id, name, password, admin, payInfo);

        String expected_name = "Balls";

        // Invoke
        user.setName(expected_name);

        // Analyze
        assertEquals(expected_name, user.getName());
    }

    @Test
    public void testType() {
        // Setup
        int id = 69;
        String name = "Deez Nuts";
        String password = "Bean";
        float admin = 0.69;
        float payInfo = 420.69;
        Ingredient user = new Ingredient(id, name, password, admin, payInfo);

        String expected_password = "Not Beans?";

        // Invoke
        user.setType(expected_password);

        // Analyze
        assertEquals(expected_password, user.getType());
    }

    @Test
    public void testPrice() {
        // Setup
        int id = 69;
        String name = "Deez Nuts";
        String password = "Bean";
        float admin = 0.69;
        float payInfo = 420.69;
        Ingredient user = new Ingredient(id, name, password, admin, payInfo);

        float expected_admin = 0.21;

        // Invoke
        user.setPrice(expected_admin);

        // Analyze
        assertEquals(expected_admin, user.getPrice());
    }

    @Test
    public void testVolume() {
        // Setup
        int id = 69;
        String name = "Deez Nuts";
        String password = "Bean";
        float admin = 0.69;
        float payInfo = 420.69;
        Ingredient user = new Ingredient(id, name, password, admin, payInfo);

        float expected_payInfo = 69.420;

        // Invoke
        user.setVolume(expected_payInfo);

        // Analyze
        assertEquals(expected_payInfo, user.getVolume());
    }

    @Test
    public void testToString() {
        // Setup
        int id = 69;
        String name = "Deez Nuts";
        String password = "Bean";
        float admin = 0.69;
        float payInfo = 420.69;
        String expected_string = String.format(Ingredient.STRING_FORMAT, id, name, password, admin, payInfo);
        Ingredient user = new Ingredient(id, name, password, admin, payInfo);

        // Invoke
        String actual_string = user.toString();

        // Analyze
        assertEquals(expected_string, actual_string);
    }
}
