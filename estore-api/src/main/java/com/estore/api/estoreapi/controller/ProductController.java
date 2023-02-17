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

import com.estore.api.estoreapi.persistence.ProductDAO;
import com.estore.api.estoreapi.model.Product;

/**
 * Handles the REST API requests for the Product resource
 * <p>
 * {@literal @}RestController Spring annotation identifies this class as a REST
 * API
 * method handler to the Spring framework
 * 
 * @author AADITH CHARUGUNDLA
 */

@RestController
@RequestMapping("products")
public class ProductController {
    private static final Logger LOG = Logger.getLogger(ProductController.class.getName());
    private ProductDAO productDao;

    /**
     * Creates a REST API controller to reponds to requests
     * 
     * @param productDao The {@link ProductDAO Product Data Access Object} to
     *                   perform CRUD operations
     *                   <br>
     *                   This dependency is injected by the Spring Framework
     */
    public ProductController(ProductDAO productDao) {
        this.productDao = productDao;
    }

    /**
     * Responds to the GET request for all {@linkplain Product products} whose name
     * contains
     * the text in name
     * 
     * @param name The name parameter which contains the text used to find the
     *             {@link Product products}
     * 
     * @return ResponseEntity with array of {@link Product product} objects (may be
     *         empty)
     *         and
     *         HTTP status of OK<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     *         <p>
     *         Example: Find all products that contain the text "ma"
     *         GET http://localhost:8080/products/?name=ma
     */
    @GetMapping("/")
    public ResponseEntity<Product[]> searchProducts(@RequestParam String name) {
        LOG.info("GET /products/?name=" + name);

        try {
            Product[] products = productDao.findProducts(name);
            if (products != null)
                return new ResponseEntity<Product[]>(products, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
