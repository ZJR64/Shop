package com.estore.api.estoreapi.Model;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import com.estore.api.estoreapi.model.Order;


/**
 * The unit test suite for the Order class
 * 
 * @author Zak Rutherford
 */
@Tag("Model-tier")
@SpringBootTest
class EstoreApiOrderTests {

	@Test
	public void testCtor() {
		// Setup
		int expected_id = 100;
		String expected_email = "example@test.com";
		String expected_address = "12345 made up road";
		String expected_payment = "1234-5678-9012-3456";
		double expected_price = 12.57;
		boolean expected_fullfilled = true;
        Map<String, Double>[] expected_products = (Map<String, Double>[]) new Map[2];
        expected_products[0] = Map.of("first ingredient", 8.0);
        expected_products[1] = Map.of("second ingredient", 12.0);

		// Invoke
		Order order = new Order(expected_id, expected_email, expected_address, expected_payment, expected_price, expected_products, expected_fullfilled);

		// Analyze
		assertEquals(expected_id, order.getId());
		assertEquals(expected_email, order.getEmail());
		assertEquals(expected_address, order.getAddress());
		assertEquals(expected_payment, order.getPayment());
		assertEquals(expected_price, order.getPrice());
		assertEquals(expected_products, order.getProducts());
		assertEquals(expected_fullfilled, order.getFullfilled());
	}

	@Test
	public void testEmail() {
		// Setup
		int id = 100;
		String email = "example@test.com";
		String address = "12345 made up road";
		String payment = "1234-5678-9012-3456";
		double price = 12.57;
        Map<String, Double>[] products = (Map<String, Double>[]) new Map[2];
        products[0] = Map.of("first ingredient", 8.0);
        products[1] = Map.of("second ingredient", 12.0);
		Order order = new Order(id, email, address, payment, price, products, true);
        String expected_email = "test@this.com";

		// Invoke
		order.setEmail(expected_email);

		// Analyze
		assertEquals(expected_email, order.getEmail());
	}

	@Test
	public void testAddress() {
		// Setup
		int id = 100;
		String email = "example@test.com";
		String address = "12345 made up road";
		String payment = "1234-5678-9012-3456";
		double price = 12.57;
        Map<String, Double>[] products = (Map<String, Double>[]) new Map[2];
        products[0] = Map.of("first ingredient", 8.0);
        products[1] = Map.of("second ingredient", 12.0);
		Order order = new Order(id, email, address, payment, price, products, true);
        String expected_address = "99999 testing boulevard";

		// Invoke
		order.setAddress(expected_address);

		// Analyze
		assertEquals(expected_address, order.getAddress());
	}

	@Test
	public void testPayment() {
		// Setup
		int id = 100;
		String email = "example@test.com";
		String address = "12345 made up road";
		String payment = "1234-5678-9012-3456";
		double price = 12.57;
        Map<String, Double>[] products = (Map<String, Double>[]) new Map[2];
        products[0] = Map.of("first ingredient", 8.0);
        products[1] = Map.of("second ingredient", 12.0);
		Order order = new Order(id, email, address, payment, price, products, false);
        String expected_payment = "0000-0000-0000-0000";

		// Invoke
		order.setPayment(expected_payment);

		// Analyze
		assertEquals(expected_payment, order.getPayment());
	}

	@Test
	public void testPrice() {
		// Setup
		int id = 100;
		String email = "example@test.com";
		String address = "12345 made up road";
		String payment = "1234-5678-9012-3456";
		double price = 12.57;
        Map<String, Double>[] products = (Map<String, Double>[]) new Map[2];
        products[0] = Map.of("first ingredient", 8.0);
        products[1] = Map.of("second ingredient", 12.0);
		Order order = new Order(id, email, address, payment, price, products, false);
        double expected_price = 1000000000;

		// Invoke
		order.setPrice(expected_price);

		// Analyze
		assertEquals(expected_price, order.getPrice());
	}

	@Test
	public void testProducts() {
		// Setup
		int id = 100;
		String email = "example@test.com";
		String address = "12345 made up road";
		String payment = "1234-5678-9012-3456";
		double price = 12.57;
        Map<String, Double>[] products = (Map<String, Double>[]) new Map[2];
        products[0] = Map.of("first ingredient", 8.0);
        products[1] = Map.of("second ingredient", 12.0);
		Order order = new Order(id, email, address, payment, price, products, true);
        Map<String, Double>[] expected_products = (Map<String, Double>[]) new Map[1];
        expected_products[0] = Map.of("test ingredient", 30.0);

		// Invoke
		order.setProducts(expected_products);

		// Analyze
		assertEquals(expected_products, order.getProducts());
	}

	@Test
	public void testFullfilled() {
		// Setup
		int id = 100;
		String email = "example@test.com";
		String address = "12345 made up road";
		String payment = "1234-5678-9012-3456";
		double price = 12.57;
        Map<String, Double>[] products = (Map<String, Double>[]) new Map[2];
        products[0] = Map.of("first ingredient", 8.0);
        products[1] = Map.of("second ingredient", 12.0);
		Order order = new Order(id, email, address, payment, price, products, false);
        boolean expected_fullfilled = true;

		// Invoke
		order.setFullfilled(expected_fullfilled);

		// Analyze
		assertEquals(expected_fullfilled, order.getFullfilled());
	}

	@Test
	public void testToString() {
		// Setup
		int id = 100;
		String email = "example@test.com";
		String address = "12345 made up road";
		String payment = "1234-5678-9012-3456";
		double price = 12.57;
		boolean fullfilled = true;
        Map<String, Double>[] products = (Map<String, Double>[]) new Map[2];
        products[0] = Map.of("first ingredient", 8.0);
        products[1] = Map.of("second ingredient", 12.0);
		Order order = new Order(id, email, address, payment, price, products, fullfilled);
        String expected_string = String.format(Order.getSTRING_FORMAT(), id, email, address, payment, price, products, fullfilled);

		// Invoke
		String actual_string = order.toString();

		// Analyze
		assertEquals(expected_string, actual_string);
	}
}
