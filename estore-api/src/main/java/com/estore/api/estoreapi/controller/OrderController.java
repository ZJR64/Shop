package com.estore.api.estoreapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.estore.api.estoreapi.persistence.OrderDAO;
import com.estore.api.estoreapi.model.Order;

/**
 * Handles the REST API requests for the Order resource
 * <p>
 * {@literal @}RestController Spring annotation identifies this class as a REST
 * API
 * method handler to the Spring framework
 * 
 * @author Zak Rutherford
 */

@RestController
@RequestMapping("orders")
public class OrderController {
    private static final Logger LOG = Logger.getLogger(OrderController.class.getName());
    private OrderDAO orderDao;

    /**
     * Creates a REST API controller to reponds to requests
     * 
     * @param orderDao The {@link OrderDAO Order Data Access Object} to
     *                   perform CRUD operations
     *                   <br>
     *                   This dependency is injected by the Spring Framework
     */
    public OrderController(OrderDAO orderDao) {
        this.orderDao = orderDao;
    }

    /**
     * Responds to the GET request for a {@linkplain Order order} for the given
     * id
     * 
     * @param id The id used to locate the {@link Order order}
     * 
     * @return ResponseEntity with {@link Order order} object and HTTP status of
     *         OK if found<br>
     *         ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable int id) {
        LOG.info("GET /order/" + id);
        try {
            Order order = orderDao.getOrder(id);
            if (order != null)
                return new ResponseEntity<Order>(order, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Creates a {@linkplain Order order} with the provided order object
     *
     * @param order - The {@link Order order} to create
     *
     * @return ResponseEntity with created {@link Order order} object and HTTP
     *         status of CREATED<br>
     *         ResponseEntity with HTTP status of CONFLICT if {@link Order
     *         order} object already exists<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("")
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        LOG.info("POST /orders " + order);
        try {
            Order newOrder = orderDao.createOrder(order);
            if (newOrder != null) {
                return new ResponseEntity<Order>(newOrder, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all {@linkplain Order orders}
     * 
     * @return ResponseEntity with array of {@link Order order} objects (may be
     *         empty) and
     *         HTTP status of OK<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("")
    public ResponseEntity<Order[]> getOrders() {
        LOG.info("GET /orders");

        try {
            Order[] orders = orderDao.getOrders();
            return new ResponseEntity<Order[]>(orders, HttpStatus.OK);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all {@linkplain Order orders} whose name
     * contains
     * the text in name
     * 
     * @param name The name parameter which contains the text used to find the
     *             {@link Order orders}
     * 
     * @return ResponseEntity with array of {@link Order hero} objects (may be
     *         empty) and
     *         HTTP status of OK<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     *         <p>
     *         Example: Find all orders that contain the text "ma"
     *         GET http://localhost:8080/orders/?name=ma
     */
    @GetMapping("/")
    public ResponseEntity<Order[]> searchOrders(@RequestParam String name) {
        LOG.info("GET /orders/?name=" + name);

        try {
            Order[] orders = orderDao.findOrders(name);
            if (orders != null) {
                return new ResponseEntity<Order[]>(orders, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Updates the {@linkplain Order order} with the provided
     * {@linkplain Order order} object, if it exists
     * 
     * @param Order order The {@link Order order} to update
     * 
     * @return ResponseEntity with updated {@link Order order} object and HTTP
     *         status of OK if updated<br>
     *         ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PutMapping("")
    public ResponseEntity<Order> updateOrder(@RequestBody Order order) {
        LOG.info("PUT /orders " + order);

        // Replace below with your implementation
        try {
            Order orderCheck = orderDao.updateOrder(order);
            if (orderCheck != null) {
                return new ResponseEntity<Order>(orderCheck, HttpStatus.OK);
            }
            return new ResponseEntity<Order>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes a {@linkplain Order order} with the given id
     * 
     * @param id The id of the {@link Order order} to deleted
     * 
     * @return ResponseEntity HTTP status of OK if deleted<br>
     *         ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Order> deleteOrder(@PathVariable int id) {
        LOG.info("DELETE /orders/" + id);

        // Replace below with your implementation
        try {
            boolean orderCheck = orderDao.deleteOrder(id);
            if (orderCheck) {
                return new ResponseEntity<Order>(HttpStatus.OK);
            }
            return new ResponseEntity<Order>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
