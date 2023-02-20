package com.estore.api.estoreapi;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.persistence.ProductFileDAO;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
class EstoreApiApplicationTests {
	ProductFileDAO productFileDAO;
    Product[] testProducts;
    ObjectMapper mockObjectMapper;

	@Test
	void contextLoads() {
	}

	@Test
    public void testUpdateHero() {
        // Setup
        
		Product product = new Product(99,"Straight Milk", "Coffee");

        // Invoke
        Product result = assertDoesNotThrow(() -> productFileDAO.updateProduct(product),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        Product actual = productFileDAO.getProduct(product.getId());
        assertEquals(actual,product);
    }

}
