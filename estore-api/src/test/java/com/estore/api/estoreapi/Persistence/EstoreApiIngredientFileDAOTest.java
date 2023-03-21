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

import com.estore.api.estoreapi.model.Ingredient;
import com.estore.api.estoreapi.persistence.IngredientFileDAO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Test the Ingredient File DAO class
 * 
 * @author SWEN Faculty
 */
@Tag("Persistence-tier")
public class EstoreApiIngredientFileDAOTest {
    IngredientFileDAO ingredientFileDAO;
    Ingredient[] testIngredients;
    ObjectMapper mockObjectMapper;

    /**
     * Before each test, we will create and inject a Mock Object Mapper to
     * isolate the tests from the underlying file
     * @throws IOException
     */
    @BeforeEach
    public void setupIngredientFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testIngredients = new Ingredient[3];
        testIngredients[0] = new Ingredient(1,"Pinto Beans","Bean","some description",0.70,100);
        testIngredients[1] = new Ingredient(2,"Black Beans","Bean","some description",1.00,230);
        testIngredients[2] = new Ingredient(3,"Cocao Beans","Bean","some description",3.25,175);

        // When the object mapper is supposed to read from the file
        // the mock object mapper will return the hero array above
        when(mockObjectMapper
            .readValue(new File("doesnt_matter.txt"),Ingredient[].class))
                .thenReturn(testIngredients);
        ingredientFileDAO = new IngredientFileDAO("doesnt_matter.txt",mockObjectMapper);
    }

    @Test
    public void testGetIngredients() {
        // Invoke
        Ingredient[] ingredients = ingredientFileDAO.getIngredients();

        // Analyze
        assertEquals(ingredients.length,testIngredients.length);
        for (int i = 0; i < testIngredients.length;++i)
            assertEquals(ingredients[i],testIngredients[i]);
    }

    @Test
    public void testFindIngredients() {
        // Invoke
        Ingredient[] ingredients = ingredientFileDAO.findIngredients("Beans");

        // Analyze
        assertEquals(ingredients.length,3);
        assertEquals(ingredients[0],testIngredients[0]);
        assertEquals(ingredients[1],testIngredients[1]);
        assertEquals(ingredients[2],testIngredients[2]);
    }

    @Test
    public void testGetIngredient() {
        // Invoke
        Ingredient ingredient = ingredientFileDAO.getIngredient(1);

        // Analzye
        assertEquals(ingredient,testIngredients[0]);
    }

    @Test
    public void testDeleteIngredient() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> ingredientFileDAO.deleteIngredient(2),
                            "Unexpected exception thrown");

        // Analzye
        assertEquals(result,true);
        // We check the internal tree map size against the length
        // of the test heroes array - 1 (because of the delete)
        // Because heroes attribute of HeroFileDAO is package private
        // we can access it directly
        assertEquals(ingredientFileDAO.ingredients.size(),testIngredients.length-1);
    }

    @Test
    public void testCreateIngredient() {
        // Setup
        Ingredient ingredient = new Ingredient(102,"Wonder-Person","Bean","some description",0.50,130);

        // Invoke
        Ingredient result = assertDoesNotThrow(() -> ingredientFileDAO.createIngredient(ingredient),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        Ingredient actual = ingredient;
        assertEquals(actual.getId(),ingredient.getId());
        assertEquals(actual.getName(),ingredient.getName());
    }

    @Test
    public void testUpdateIngredient() {
        // Setup
        Ingredient ingredient = new Ingredient(1,"update ingredient","Bean","some description",13.50,85);

        // Invoke
        Ingredient result = assertDoesNotThrow(() -> ingredientFileDAO.updateIngredient(ingredient),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        Ingredient actual = ingredientFileDAO.getIngredient(ingredient.getId());
        assertEquals(actual,ingredient);
    }

    @Test
    public void testSaveException() throws IOException{
        doThrow(new IOException())
            .when(mockObjectMapper)
                .writeValue(any(File.class),any(Ingredient[].class));

        Ingredient ingredient = new Ingredient(102,"Wi-Fire","Beans","some description",12.00,135);

        assertThrows(IOException.class,
                        () -> ingredientFileDAO.createIngredient(ingredient),
                        "IOException not thrown");
    }

    @Test
    public void testGetIngredientNotFound() {
        // Invoke
        Ingredient ingredient = ingredientFileDAO.getIngredient(98);

        // Analyze
        assertEquals(ingredient,null);
    }

    @Test
    public void testDeleteIngredientNotFound() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> ingredientFileDAO.deleteIngredient(98),
                                                "Unexpected exception thrown");

        // Analyze
        assertEquals(result,false);
        assertEquals(ingredientFileDAO.ingredients.size(),testIngredients.length);
    }

    @Test
    public void testUpdateIngredientNotFound() {
        // Setup
        Ingredient ingredient = new Ingredient(98,"Bolt","Beans","some description",1.35,260);

        // Invoke
        Ingredient result = assertDoesNotThrow(() -> ingredientFileDAO.updateIngredient(ingredient),
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
        // from the IngredientFileDAO load method, an IOException is
        // raised
        doThrow(new IOException())
            .when(mockObjectMapper)
                .readValue(new File("doesnt_matter.txt"),Ingredient[].class);

        // Invoke & Analyze
        assertThrows(IOException.class,
                        () -> new IngredientFileDAO("doesnt_matter.txt",mockObjectMapper),
                        "IOException not thrown");
    }
}
