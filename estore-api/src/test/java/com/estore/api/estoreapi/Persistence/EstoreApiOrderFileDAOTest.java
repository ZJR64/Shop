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
import java.util.*;

import com.estore.api.estoreapi.model.Order;
import com.estore.api.estoreapi.persistence.OrderFileDAO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Test the Order File DAO class
 * 
 * @author SWEN Faculty
 */
@Tag("Persistence-tier")
public class EstoreApiOrderFileDAOTest {
    OrderFileDAO orderFileDAO;
    Order[] testOrders;
    ObjectMapper mockObjectMapper;

    /**
     * Before each test, we will create and inject a Mock Object Mapper to
     * isolate the tests from the underlying file
     * @throws IOException
     */
    @BeforeEach
    public void setupOrderFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testOrders = new Order[3];
        Map<String, Double[]> products1 = new HashMap<String, Double[]>();
        Double[] values1 = {8.0, 12.9};
        products1.put("product 1", values1);
        Map<String, Double[]> products2 = new HashMap<String, Double[]>();
        Double[] values2 = {1.0, 15.2};
        products2.put("product 2", values2);
        Map<String, Double[]> products0 = new HashMap<String, Double[]>();
        Double[] values3 = {8.0, 12.9};
        Double[] values4 = {32.0};
        products0.put("product 3", values3);
        products0.put("product 4", values4);

		testOrders[0] = new Order(98, "example@test.com", "12345 made up road", "1234-5678-9012-3456", 12.57, products0, true);
        testOrders[1] = new Order(99, "test@fake.net", "99999 not a gov secret", "1111-1111-1111-1111", 5000.99, products1, false);
        testOrders[2] = new Order(100, "null@no.thing", "oopse, no address", "xxxx-xxxx-xxxx-xxxx", 0.0, products2, true);

        // When the object mapper is supposed to read from the file
        // the mock object mapper will return the order array above
        when(mockObjectMapper
            .readValue(new File("doesnt_matter.txt"),Order[].class))
                .thenReturn(testOrders);
        orderFileDAO = new OrderFileDAO("doesnt_matter.txt",mockObjectMapper);
    }

    @Test
    public void testGetOrders() {
        // Invoke
        Order[] orders = orderFileDAO.getOrders();

        // Analyze
        assertEquals(orders.length,testOrders.length);
        for (int i = 0; i < testOrders.length;++i)
            assertEquals(orders[i],testOrders[i]);
    }

    @Test
    public void testFindOrders() {
        // Invoke
        Order[] orders = orderFileDAO.findOrders(".");

        // Analyze
        assertEquals(orders.length,3);
        assertEquals(orders[0],testOrders[0]);
        assertEquals(orders[1],testOrders[1]);
        assertEquals(orders[2],testOrders[2]);
    }

    @Test
    public void testGetOrder() {
        // Invoke
        Order order = orderFileDAO.getOrder(98);

        // Analzye
        assertEquals(order,testOrders[0]);
    }

    @Test
    public void testDeleteOrder() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> orderFileDAO.deleteOrder(98),
                            "Unexpected exception thrown");

        // Analzye
        assertEquals(result,true);
        // We check the internal tree map size against the length
        // of the test heroes array - 1 (because of the delete)
        // Because heroes attribute of HeroFileDAO is package private
        // we can access it directly
        assertEquals(orderFileDAO.orders.size(),testOrders.length-1);
    }

    @Test
    public void testCreateOrder() {
        // Setup
        Order order = new Order(1, "new@email.aaahhh", "new phone who dis", "9632-4745-0183", 864.44, null, false);

        // Invoke
        Order result = assertDoesNotThrow(() -> orderFileDAO.createOrder(order),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        Order actual = order;
        assertEquals(actual.getId(),order.getId());
        assertEquals(actual.getEmail(),order.getEmail());
    }

    @Test
    public void testUpdateOrder() {
        // Setup
        Map<String, Double[]> products = new HashMap<String, Double[]>();
        Double[] values = {8.0, 12.9};
        products.put("product 1", values);
        Order order = new Order(100, "new@email.wooo", "new phone who dis 2", "9632-4745-0183", 864.55, products, true);

        // Invoke
        Order result = assertDoesNotThrow(() -> orderFileDAO.updateOrder(order),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        Order actual = orderFileDAO.getOrder(order.getId());
        assertEquals(actual,order);
    }

    @Test
    public void testSaveException() throws IOException{
        doThrow(new IOException())
            .when(mockObjectMapper)
                .writeValue(any(File.class),any(Order[].class));

        Order order = new Order(10000, "new@email.wooo", "new phone who dis 2", "9632-4745-0183", 864.55, null, false);

        assertThrows(IOException.class,
                        () -> orderFileDAO.createOrder(order),
                        "IOException not thrown");
    }

    @Test
    public void testGetOrderNotFound() {
        // Invoke
        Order order = orderFileDAO.getOrder(1);

        // Analyze
        assertEquals(order,null);
    }

    @Test
    public void testDeleteOrderNotFound() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> orderFileDAO.deleteOrder(1),
                                                "Unexpected exception thrown");

        // Analyze
        assertEquals(result,false);
        assertEquals(orderFileDAO.orders.size(),testOrders.length);
    }

    @Test
    public void testUpdateOrderNotFound() {
        // Setup
        Order order = new Order(1000, "new@email.wooo", "new phone who dis 2", "9632-4745-0183", 864.55, null, false);

        // Invoke
        Order result = assertDoesNotThrow(() -> orderFileDAO.updateOrder(order),
                                                "Unexpected exception thrown");

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
        // from the OrderFileDAO load method, an IOException is
        // raised
        doThrow(new IOException())
            .when(mockObjectMapper)
                .readValue(new File("doesnt_matter.txt"),Order[].class);

        // Invoke & Analyze
        assertThrows(IOException.class,
                        () -> new OrderFileDAO("doesnt_matter.txt",mockObjectMapper),
                        "IOException not thrown");
    }
}
