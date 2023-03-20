package com.estore.api.estoreapi.Model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import java.util.*;

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
class EstoreApiUserTests {

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
        double[] temp = new double[] {10.0, 27.0 };
        Map<String, double[]> expected_cart = Map.of("Test Blend", temp);

        // Invoke
        User user = new User(expected_id, expected_email, expected_name, expected_password, expected_address,
                expected_admin, expected_payInfo, expected_cart);

        // Analyze
        assertEquals(expected_id, user.getId());
        assertEquals(expected_email, user.getEmail());
        assertEquals(expected_name, user.getName());
        assertEquals(expected_password, user.getPassword());
        assertEquals(expected_admin, user.getAdmin());
        assertEquals(expected_payInfo, user.getPayInfo());
        assertEquals(expected_cart, user.getCart());

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
        double[] temp = new double[] {10.0, 27.0 };
        Map<String, double[]> cart = Map.of("Test Blend", temp);
        User user = new User(id, email, name, password, address, admin, payInfo, cart);

        String expected_name = "Balls";

        // Invoke
        user.setName(expected_name);

        // Analyze
        assertEquals(expected_name, user.getName());
    }

    @Test
    public void testAddress() {
        // Setup
        int id = 69;
        String email = "scammer@scam.com";
        String name = "Deez Nuts";
        String password = "password123";
        String address = "129 balls lane";
        Boolean admin = false;
        String[] payInfo = new String[] { "123", "235", "1351135" };
        double[] temp = new double[] {10.0, 27.0 };
        Map<String, double[]> cart = Map.of("Test Blend", temp);
        User user = new User(id, email, name, password, address, admin, payInfo, cart);

        String expected_address = "Testicular Torsion Avenue";

        // Invoke
        user.setAddress(expected_address);

        // Analyze
        assertEquals(expected_address, user.getAddress());
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
        double[] temp = new double[] {10.0, 27.0 };
        Map<String, double[]> cart = Map.of("Test Blend", temp);
        User user = new User(id, email, name, password, address, admin, payInfo, cart);

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
        double[] temp = new double[] {10.0, 27.0 };
        Map<String, double[]> cart = Map.of("Test Blend", temp);
        User user = new User(id, email, name, password, address, admin, payInfo, cart);

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
        double[] temp = new double[] {10.0, 27.0 };
        Map<String, double[]> cart = Map.of("Test Blend", temp);
        User user = new User(id, email, name, password, address, admin, payInfo, cart);

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
        double[] temp = new double[] {10.0, 27.0 };
        Map<String, double[]> cart = Map.of("Test Blend", temp);
        String expected_string = String.format(User.STRING_FORMAT, id, email, name, password, address, admin, payInfo, cart);
        User user = new User(id, email, name, password, address, admin, payInfo, cart);

        // Invoke
        String actual_string = user.toString();

        // Analyze
        assertEquals(expected_string, actual_string);
    }
}
