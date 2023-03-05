package com.estore.api.estoreapi.model.ProductTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.estore.api.estoreapi.model.Product;

import java.util.HashMap;
import java.util.Map;

@Tag("Model-Tier")
public class EstoreApiProductTests {
    //initialize the test object
    int expectedId = 77;
    String expectedName = "productName";
    String expectedType = "productType";
    float expectedModPrice = (float) 6.9;
    Map<String, Float> expectedIngredients = Map.of("testKey", (float)77);

    private Product testProduct = new Product(expectedId, expectedName, expectedType, expectedModPrice, expectedIngredients);

    @Test
    public void testGets(){
        //test the get methods
        // get ID
        assertEquals(expectedId, testProduct.getId());
        //get name
        assertEquals(expectedName, testProduct.getName());
        //get type
        assertEquals(expectedType, testProduct.getType());
        //get modPrice
        assertEquals(expectedModPrice, testProduct.getModPrice());
        //get the ingredients
        assertEquals(expectedIngredients, testProduct.getIngredients());
    }

    @Test
    public void testSets(){
        //testing the set methods
        //change the already initialized variables, and use the set methods and then run the get tests again
        expectedId = 78;
        expectedName = "productNameChanged";
        expectedType = "productTypeChanged";
        expectedModPrice = (float)42;

        //now change the values in testproduct;
        /*testProduct.setId(expectedId);
         * commented out because it's unclear if the Id is changable, because there's no setId() method*/
        testProduct.setName(expectedName);
        testProduct.setType(expectedType);
        testProduct.setIngredients(Map.of("test2", 17.0));
        testProduct.setModPrice(expectedModPrice);
        testGets();

    }

}
