package com.estore.api.estoreapi.Model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.estore.api.estoreapi.model.User;

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
        assertEquals(expected_password, user.getPassword());
        assertEquals(expected_admin, user.getAdmin());
        assertEquals(expected_payInfo, user.getPayInfo());

    }

    @Test
    public void testName() {
        // Setup
        int id = 69;
        String email = "scammer@scam.com";
        String name = "Deez Nuts";
        String password = "password123";
        String address = "129 balls lane";
        Boolean admin = false;
        String[] payInfo = new String[] { "123", "235", "1351135" };
        User user = new User(id, email, name, password, address, admin, payInfo);

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
        String email = "scammer@scam.com";
        String name = "Deez Nuts";
        String password = "password123";
        String address = "129 balls lane";
        Boolean admin = false;
        String[] payInfo = new String[] { "123", "235", "1351135" };
        User user = new User(id, email, name, password, address, admin, payInfo);

        String expected_password = "Not Beans?";

        // Invoke
        user.setPassword(expected_password);

        // Analyze
        assertEquals(expected_password, user.getPassword());
    }

    @Test
    public void testPrice() {
        // Setup
        int id = 69;
        String email = "scammer@scam.com";
        String name = "Deez Nuts";
        String password = "password123";
        String address = "129 balls lane";
        Boolean admin = false;
        String[] payInfo = new String[] { "123", "235", "1351135" };
        User user = new User(id, email, name, password, address, admin, payInfo);

        boolean expected_admin = true;
        // Invoke
        user.setAdmin(expected_admin);

        // Analyze
        assertEquals(expected_admin, user.getAdmin());
    }

    @Test
    public void testVolume() {
        // Setup
        int id = 69;
        String email = "scammer@scam.com";
        String name = "Deez Nuts";
        String password = "password123";
        String address = "129 balls lane";
        Boolean admin = false;
        String[] payInfo = new String[] { "123", "235", "1351135" };
        User user = new User(id, email, name, password, address, admin, payInfo);

        String[] expected_payInfo = new String[] { "135135", "13616457", "9876" };

        // Invoke
        user.setPayInfo(expected_payInfo);

        // Analyze
        assertEquals(expected_payInfo, user.getPayInfo());
    }

    @Test
    public void testToString() {
        // Setup
        int id = 69;
        String email = "scammer@scam.com";
        String name = "Deez Nuts";
        String password = "password123";
        String address = "129 balls lane";
        Boolean admin = false;
        String[] payInfo = new String[] { "123", "235", "1351135" };
        String expected_string = String.format(User.STRING_FORMAT, id, name, password, admin, payInfo);
        User user = new User(id, email, name, password, address, admin, payInfo);

        // Invoke
        String actual_string = user.toString();

        // Analyze
        assertEquals(expected_string, actual_string);
    }
}
