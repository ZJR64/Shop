package com.estore.api.estoreapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.estore.api.estoreapi.persistence.OrderDAO;
import com.estore.api.estoreapi.model.Order;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Test the Order Controller class
 * 
 * @author Cullen
 */
@Tag("Controller-tier")
public class OrderControllerTest {
    private OrderController orderController;
    private OrderDAO mockOrderDAO;

    /**
     * Before each test, create a new OrderController object and inject
     * a mock Order DAO
     */
    @BeforeEach
    public void setupOrderController() {
        mockOrderDAO = mock(OrderDAO.class);
        orderController = new OrderController(mockOrderDAO);
    }

    @Test
    public void testGetOrder() throws IOException {  // getOrder may throw IOException
        // Setup
        Map<String, Double[]> products = new HashMap<String, Double[]>();
        Double[] values = {8.0, 12.9};
        products.put("product 1", values);
        Order order = new Order(100, "new@email.wooo", "new phone who dis 2", "9632-4745-0183", 864.55, products, true);
        // When the same id is passed in, our mock Order DAO will return the Order object
        when(mockOrderDAO.getOrder(order.getId())).thenReturn(order);

        // Invoke
        ResponseEntity<Order> response = orderController.getOrder(order.getId());

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(order,response.getBody());
    }

    @Test
    public void testGetOrderNotFound() throws Exception { // createOrder may throw IOException
        // Setup
        int orderId = 99;
        // When the same id is passed in, our mock Order DAO will return null, simulating
        // no order found
        when(mockOrderDAO.getOrder(orderId)).thenReturn(null);

        // Invoke
        ResponseEntity<Order> response = orderController.getOrder(orderId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testGetOrderHandleException() throws Exception { // createOrder may throw IOException
        // Setup
        int orderId = 99;
        // When getOrder is called on the Mock Order DAO, throw an IOException
        doThrow(new IOException()).when(mockOrderDAO).getOrder(orderId);

        // Invoke
        ResponseEntity<Order> response = orderController.getOrder(orderId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testCreateOrder() throws IOException {  // createOrder may throw IOException
        // Setup
        Map<String, Double[]> products = new HashMap<String, Double[]>();
        Double[] values = {8.0, 12.9};
        products.put("product 1", values);
        Order order = new Order(100, "new@email.wooo", "new phone who dis 2", "9632-4745-0183", 864.55, products, true);
        // when createOrder is called, return true simulating successful
        // creation and save
        when(mockOrderDAO.createOrder(order)).thenReturn(order);

        // Invoke
        ResponseEntity<Order> response = orderController.createOrder(order);

        // Analyze
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals(order,response.getBody());
    }

    @Test
    public void testCreateOrderFailed() throws IOException {  // createOrder may throw IOException
        // Setup
        Map<String, Double[]> products = new HashMap<String, Double[]>();
        Double[] values = {8.0, 12.9};
        products.put("product 1", values);
        Order order = new Order(100, "new@email.wooo", "new phone who dis 2", "9632-4745-0183", 864.55, products, true);
        // when createOrder is called, return false simulating failed
        // creation and save
        when(mockOrderDAO.createOrder(order)).thenReturn(null);

        // Invoke
        ResponseEntity<Order> response = orderController.createOrder(order);

        // Analyze
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
    }

    @Test
    public void testCreateOrderHandleException() throws IOException {  // createOrder may throw IOException
        // Setup
        Map<String, Double[]> products = new HashMap<String, Double[]>();
        Double[] values = {8.0, 12.9};
        products.put("product 1", values);
        Order order = new Order(100, "new@email.wooo", "new phone who dis 2", "9632-4745-0183", 864.55, products, true);

        // When createOrder is called on the Mock Order DAO, throw an IOException
        doThrow(new IOException()).when(mockOrderDAO).createOrder(order);

        // Invoke
        ResponseEntity<Order> response = orderController.createOrder(order);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testUpdateOrder() throws IOException { // updateOrder may throw IOException
        // Setup
        Map<String, Double[]> products = new HashMap<String, Double[]>();
        Double[] values = {8.0, 12.9};
        products.put("product 1", values);
        Order order = new Order(100, "new@email.wooo", "new phone who dis 2", "9632-4745-0183", 864.55, products, true);
        // when updateOrder is called, return true simulating successful
        // update and save
        when(mockOrderDAO.updateOrder(order)).thenReturn(order);
        ResponseEntity<Order> response = orderController.updateOrder(order);
        order.setEmail("test@this.net");

        // Invoke
        response = orderController.updateOrder(order);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(order,response.getBody());
    }

    @Test
    public void testUpdateOrderFailed() throws IOException { // updateOrder may throw IOException
        // Setup
        Map<String, Double[]> products = new HashMap<String, Double[]>();
        Double[] values = {8.0, 12.9};
        products.put("product 1", values);
        Order order = new Order(100, "new@email.wooo", "new phone who dis 2", "9632-4745-0183", 864.55, products, true);
        // when updateOrder is called, return true simulating successful
        // update and save
        when(mockOrderDAO.updateOrder(order)).thenReturn(null);

        // Invoke
        ResponseEntity<Order> response = orderController.updateOrder(order);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testUpdateOrderHandleException() throws IOException { // updateOrder may throw IOException
        // Setup
        Map<String, Double[]> products = new HashMap<String, Double[]>();
        Double[] values = {8.0, 12.9};
        products.put("product 1", values);
        Order order = new Order(100, "new@email.wooo", "new phone who dis 2", "9632-4745-0183", 864.55, products, true);
        // When updateOrder is called on the Mock Order DAO, throw an IOException
        doThrow(new IOException()).when(mockOrderDAO).updateOrder(order);

        // Invoke
        ResponseEntity<Order> response = orderController.updateOrder(order);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testGetOrders() throws IOException { // getOrders may throw IOException
        // Setup
        Order[] orders = new Order[3];
        Map<String, Double[]> products1 = new HashMap<String, Double[]>();
        Double[] values1 = {8.0, 12.9};
        products1.put("product 1", values1);
        Map<String, Double[]> products2 = new HashMap<String, Double[]>();
        Double[] values2 = {1.0, 15.2};
        products2.put("product 2", values2);
        Map<String, Double[]> products3 = new HashMap<String, Double[]>();
        Double[] values3 = {8.0, 12.9};
        Double[] values4 = {32.0};
        products3.put("product 3", values3);
        products3.put("product 4", values4);

		orders[0] = new Order(98, "example@test.com", "12345 made up road", "1234-5678-9012-3456", 12.57, products3, true);
        orders[1] = new Order(99, "test@fake.net", "99999 not a gov secret", "1111-1111-1111-1111", 5000.99, products1, true);
        orders[2] = new Order(100, "null@no.thing", "oopse, no address", "xxxx-xxxx-xxxx-xxxx", 0.0, products2, true);
        // When getOrders is called return the orders created above
        when(mockOrderDAO.getOrders()).thenReturn(orders);

        // Invoke
        ResponseEntity<Order[]> response = orderController.getOrders();

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(orders,response.getBody());
    }

    @Test
    public void testGetOrdersHandleException() throws IOException { // getOrders may throw IOException
        // Setup
        // When getOrders is called on the Mock Order DAO, throw an IOException
        doThrow(new IOException()).when(mockOrderDAO).getOrders();

        // Invoke
        ResponseEntity<Order[]> response = orderController.getOrders();

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testSearchOrders() throws IOException { // findOrders may throw IOException
        // Setup
        String searchString = ".com";
        Order[] orders = new Order[3];
        Map<String, Double[]> products1 = new HashMap<String, Double[]>();
        Double[] values1 = {8.0, 12.9};
        products1.put("product 1", values1);
        Map<String, Double[]> products2 = new HashMap<String, Double[]>();
        Double[] values2 = {1.0, 15.2};
        products2.put("product 2", values2);
        Map<String, Double[]> products3 = new HashMap<String, Double[]>();
        Double[] values3 = {8.0, 12.9};
        Double[] values4 = {32.0};
        products3.put("product 3", values3);
        products3.put("product 4", values4);

		orders[0] = new Order(98, "example@test.com", "12345 made up road", "1234-5678-9012-3456", 12.57, products3, true);
        orders[1] = new Order(99, "test@fake.com", "99999 not a gov secret", "1111-1111-1111-1111", 5000.99, products1, true);
        orders[2] = new Order(100, "null@no.com", "oopse, no address", "xxxx-xxxx-xxxx-xxxx", 0.0, products2, true);
        // When findOrders is called with the search string, return the three
        /// orders above
        when(mockOrderDAO.findOrders(searchString)).thenReturn(orders);

        // Invoke
        ResponseEntity<Order[]> response = orderController.searchOrders(searchString);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(orders,response.getBody());
    }

    @Test
    public void testSearchOrdersHandleException() throws IOException { // findOrders may throw IOException
        // Setup
        String searchString = "an";
        // When createOrder is called on the Mock Order DAO, throw an IOException
        doThrow(new IOException()).when(mockOrderDAO).findOrders(searchString);

        // Invoke
        ResponseEntity<Order[]> response = orderController.searchOrders(searchString);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testSearchOrdersFailed() throws IOException { // findOrders may throw IOException
        // Setup
        String searchString = "ko";
        Order[] orders = null;
        // when updateOrder is called, return true simulating successful
        // update and save
        /// orders above
        when(mockOrderDAO.findOrders(searchString)).thenReturn(orders);

        // Invoke
        ResponseEntity<Order[]> response = orderController.searchOrders(searchString);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testDeleteOrder() throws IOException { // deleteOrder may throw IOException
        // Setup
        int orderId = 99;
        // when deleteOrder is called return true, simulating successful deletion
        when(mockOrderDAO.deleteOrder(orderId)).thenReturn(true);

        // Invoke
        ResponseEntity<Order> response = orderController.deleteOrder(orderId);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void testDeleteOrderNotFound() throws IOException { // deleteOrder may throw IOException
        // Setup
        int orderId = 99;
        // when deleteOrder is called return false, simulating failed deletion
        when(mockOrderDAO.deleteOrder(orderId)).thenReturn(false);

        // Invoke
        ResponseEntity<Order> response = orderController.deleteOrder(orderId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testDeleteOrderHandleException() throws IOException { // deleteOrder may throw IOException
        // Setup
        int orderId = 99;
        // When deleteOrder is called on the Mock Order DAO, throw an IOException
        doThrow(new IOException()).when(mockOrderDAO).deleteOrder(orderId);

        // Invoke
        ResponseEntity<Order> response = orderController.deleteOrder(orderId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }
}
