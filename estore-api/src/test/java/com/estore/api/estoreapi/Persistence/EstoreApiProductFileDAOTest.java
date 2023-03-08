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

import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.persistence.ProductFileDAO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Test the Product File DAO class
 * 
 * @author SWEN Faculty
 */
@Tag("Persistence-tier")
public class EstoreApiProductFileDAOTest {
    ProductFileDAO productFileDAO;
    Product[] testProducts;
    ObjectMapper mockObjectMapper;

    /**
     * Before each test, we will create and inject a Mock Object Mapper to
     * isolate the tests from the underlying file
     * @throws IOException
     */
    @BeforeEach
    public void setupProductFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testProducts = new Product[3];
        testProducts[0] = new Product(10, "chooch Blend", "Coffee", 12.50, null);
        testProducts[1] = new Product(11, "Gas Tea", "Tea", 13.50, null);
        testProducts[2] = new Product(12, "cloud9", "Coffee", 10.00, null);

        // When the object mapper is supposed to read from the file
        // the mock object mapper will return the Product array above
        when(mockObjectMapper
            .readValue(new File("doesnt_matter.txt"),Product[].class))
                .thenReturn(testProducts);
            
                productFileDAO = new ProductFileDAO("doesnt_matter.txt", mockObjectMapper);
        
    }

    @Test
    public void testGetProducts() {
        // Invoke
        Product[] products = productFileDAO.getProducts();

        // Analyze
        assertEquals(products.length,testProducts.length);
        for (int i = 0; i < testProducts.length;++i)
            assertEquals(products[i],testProducts[i]);
    }

    @Test
    public void testFindProducts() {
        // Invoke
        Product[] products = productFileDAO.findProducts("chooch");

        // Analyze
        assertEquals(products.length,1);
        assertEquals(products[0],testProducts[0]);
    }

    @Test
    public void testGetProduct() {
        // Invoke
        Product product = productFileDAO.getProduct(12);

        // Analzye
        assertEquals(product,testProducts[2]);
    }

    @Test
    public void testDeleteProduct() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> productFileDAO.deleteProduct(11),
                            "Unexpected exception thrown");

        // Analzye
        assertEquals(result,true);
        // We check the internal tree map size against the length
        // of the test Products array - 1 (because of the delete)
        // Because Product attribute of ProductFileDAO is package private
        // we can access it directly
        assertEquals(productFileDAO.products.size(),testProducts.length-1);
    }

    @Test
    public void testCreateProduct() {
        // Setup
        // make array of strings
        Product product = new Product(14, "gang blend", "Coffee", 20.00, null);

        // Invoke
        Product result = assertDoesNotThrow(() -> productFileDAO.createProduct(product),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        Product actual = product;
        assertEquals(actual.getId(),product.getId());
        assertEquals(actual.getName(),product.getName());
    }

    @Test
    public void testUpdateProduct() {
        // Setup

        Product product = new Product(10, "gang blend", "Coffee", 21.00, null);

        // Invoke
        Product result = assertDoesNotThrow(() -> productFileDAO.updateProduct(product),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        Product actual = productFileDAO.getProduct(product.getId());
        assertEquals(actual,product);
    }

    @Test
    public void testSaveException() throws IOException{

        doThrow(new IOException())
            .when(mockObjectMapper)
                .writeValue(any(File.class),any(Product[].class));

            
                Product product = new Product(16, "19 Dollar blend", "Coffee", 21.00, null);

        assertThrows(IOException.class,
                        () -> productFileDAO.createProduct(product),
                        "IOException not thrown");
    }

    @Test
    public void testGetProductNotFound() {
        // Invoke
        Product product = productFileDAO.getProduct(420);

        // Analyze
        assertEquals(product,null);
    }

    @Test
    public void testDeleteProductNotFound() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> productFileDAO.deleteProduct(16),
                                                "Unexpected exception thrown");

        // Analyze
        assertEquals(result,false);
        assertEquals(productFileDAO.products.size(),testProducts.length);
    }

    @Test
    public void testUpdateProductNotFound() {
        // Setup

        Product product = new Product(79, "gang blend", "Coffee", 20.00, null);

        // Invoke
        Product result = assertDoesNotThrow(() -> productFileDAO.updateProduct(product), "Unexpected exception thrown");

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
        // from the ProductFileDAO load method, an IOException is
        // raised
        doThrow(new IOException())
            .when(mockObjectMapper)
                .readValue(new File("doesnt_matter.txt"),Product[].class);

        // Invoke & Analyze
        assertThrows(IOException.class,
                        () -> new ProductFileDAO("doesnt_matter.txt",mockObjectMapper),
                        "IOException not thrown");
    }
}
