package com.estore.api.estoreapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import com.estore.api.estoreapi.persistence.IngredientDAO;
import com.estore.api.estoreapi.model.Ingredient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Test the Ingredient Controller class
 * 
 * @author Cullen
 */
@Tag("Controller-tier")
public class IngredientControllerTest {
    private IngredientController ingredientController;
    private IngredientDAO mockIngredientDAO;

    /**
     * Before each test, create a new IngredientController object and inject
     * a mock Ingredient DAO
     */
    @BeforeEach
    public void setupIngredientController() {
        mockIngredientDAO = mock(IngredientDAO.class);
        ingredientController = new IngredientController(mockIngredientDAO);
    }

    @Test
    public void testGetIngredient() throws IOException {  // getIngredient may throw IOException
        // Setup
        Ingredient ingredient = new Ingredient(99,"Blackest Coffee", "Coffee", 0.67, 10000);
        // When the same id is passed in, our mock Ingredient DAO will return the Ingredient object
        when(mockIngredientDAO.getIngredient(ingredient.getId())).thenReturn(ingredient);

        // Invoke
        ResponseEntity<Ingredient> response = ingredientController.getIngredient(ingredient.getId());

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(ingredient,response.getBody());
    }

    @Test
    public void testGetIngredientNotFound() throws Exception { // createIngredient may throw IOException
        // Setup
        int ingredientId = 99;
        // When the same id is passed in, our mock Ingredient DAO will return null, simulating
        // no ingredient found
        when(mockIngredientDAO.getIngredient(ingredientId)).thenReturn(null);

        // Invoke
        ResponseEntity<Ingredient> response = ingredientController.getIngredient(ingredientId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testGetIngredientHandleException() throws Exception { // createIngredient may throw IOException
        // Setup
        int ingredientId = 99;
        // When getIngredient is called on the Mock Ingredient DAO, throw an IOException
        doThrow(new IOException()).when(mockIngredientDAO).getIngredient(ingredientId);

        // Invoke
        ResponseEntity<Ingredient> response = ingredientController.getIngredient(ingredientId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testCreateIngredient() throws IOException {  // createIngredient may throw IOException
        // Setup
        Ingredient ingredient = new Ingredient(99,"Not Coffee Leaf", "Tea", 0.67, 10000);
        // when createIngredient is called, return true simulating successful
        // creation and save
        when(mockIngredientDAO.createIngredient(ingredient)).thenReturn(ingredient);

        // Invoke
        ResponseEntity<Ingredient> response = ingredientController.createIngredient(ingredient);

        // Analyze
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals(ingredient,response.getBody());
    }

    @Test
    public void testCreateIngredientFailed() throws IOException {  // createIngredient may throw IOException
        // Setup
        Ingredient ingredient = new Ingredient(99,"Failure Fuel", "Coffee", 0.01, 1000);
        // when createIngredient is called, return false simulating failed
        // creation and save
        when(mockIngredientDAO.createIngredient(ingredient)).thenReturn(null);

        // Invoke
        ResponseEntity<Ingredient> response = ingredientController.createIngredient(ingredient);

        // Analyze
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
    }

    @Test
    public void testCreateIngredientHandleException() throws IOException {  // createIngredient may throw IOException
        // Setup
        Ingredient ingredient = new Ingredient(99,"Failure Fuel", "Coffee", 0.01, 1000);

        // When createIngredient is called on the Mock Ingredient DAO, throw an IOException
        doThrow(new IOException()).when(mockIngredientDAO).createIngredient(ingredient);

        // Invoke
        ResponseEntity<Ingredient> response = ingredientController.createIngredient(ingredient);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testUpdateIngredient() throws IOException { // updateIngredient may throw IOException
        // Setup
        Ingredient ingredient = new Ingredient(99,"The Newest Grind", "Tea", 4.20, 100000);
        // when updateIngredient is called, return true simulating successful
        // update and save
        when(mockIngredientDAO.updateIngredient(ingredient)).thenReturn(ingredient);
        ResponseEntity<Ingredient> response = ingredientController.updateIngredient(ingredient);
        ingredient.setName("Bolt");

        // Invoke
        response = ingredientController.updateIngredient(ingredient);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(ingredient,response.getBody());
    }

    @Test
    public void testUpdateIngredientFailed() throws IOException { // updateIngredient may throw IOException
        // Setup
        Ingredient ingredient = new Ingredient(99,"The Newest Failure", "Tea", 4.69, 100000);
        // when updateIngredient is called, return true simulating successful
        // update and save
        when(mockIngredientDAO.updateIngredient(ingredient)).thenReturn(null);

        // Invoke
        ResponseEntity<Ingredient> response = ingredientController.updateIngredient(ingredient);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testUpdateIngredientHandleException() throws IOException { // updateIngredient may throw IOException
        // Setup
        Ingredient ingredient = new Ingredient(99,"The Newest Exception", "Tea", 4.20, 100000);
        // When updateIngredient is called on the Mock Ingredient DAO, throw an IOException
        doThrow(new IOException()).when(mockIngredientDAO).updateIngredient(ingredient);

        // Invoke
        ResponseEntity<Ingredient> response = ingredientController.updateIngredient(ingredient);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testGetIngredients() throws IOException { // getIngredients may throw IOException
        // Setup
        Ingredient[] ingredients = new Ingredient[2];
        ingredients[0] = new Ingredient(99,"Is that even a bean?!?!", "Coffee", 0.01, 567);
        ingredients[1] = new Ingredient(100,"Oh god, another", "Coffee", 0.02, 123);
        // When getIngredients is called return the ingredients created above
        when(mockIngredientDAO.getIngredients()).thenReturn(ingredients);

        // Invoke
        ResponseEntity<Ingredient[]> response = ingredientController.getIngredients();

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(ingredients,response.getBody());
    }

    @Test
    public void testGetIngredientsHandleException() throws IOException { // getIngredients may throw IOException
        // Setup
        // When getIngredients is called on the Mock Ingredient DAO, throw an IOException
        doThrow(new IOException()).when(mockIngredientDAO).getIngredients();

        // Invoke
        ResponseEntity<Ingredient[]> response = ingredientController.getIngredients();

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testSearchIngredients() throws IOException { // findIngredients may throw IOException
        // Setup
        String searchString = "Thee";
        Ingredient[] ingredients = new Ingredient[2];
        ingredients[0] = new Ingredient(99,"For Thee Search", "Coffee", 0.01, 567);
        ingredients[1] = new Ingredient(100,"Thee Will Never Be Found", "Coffee", 0.02, 123);
        // When findIngredients is called with the search string, return the two
        /// ingredients above
        when(mockIngredientDAO.findIngredients(searchString)).thenReturn(ingredients);

        // Invoke
        ResponseEntity<Ingredient[]> response = ingredientController.searchIngredients(searchString);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(ingredients,response.getBody());
    }

    @Test
    public void testSearchIngredientsHandleException() throws IOException { // findIngredients may throw IOException
        // Setup
        String searchString = "an";
        // When createIngredient is called on the Mock Ingredient DAO, throw an IOException
        doThrow(new IOException()).when(mockIngredientDAO).findIngredients(searchString);

        // Invoke
        ResponseEntity<Ingredient[]> response = ingredientController.searchIngredients(searchString);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testSearchIngredientsFailed() throws IOException { // findIngredients may throw IOException
        // Setup
        String searchString = "ko";
        Ingredient[] ingredients = null;
        // when updateIngredient is called, return true simulating successful
        // update and save
        /// ingredients above
        when(mockIngredientDAO.findIngredients(searchString)).thenReturn(ingredients);

        // Invoke
        ResponseEntity<Ingredient[]> response = ingredientController.searchIngredients(searchString);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testDeleteIngredient() throws IOException { // deleteIngredient may throw IOException
        // Setup
        int ingredientId = 99;
        // when deleteIngredient is called return true, simulating successful deletion
        when(mockIngredientDAO.deleteIngredient(ingredientId)).thenReturn(true);

        // Invoke
        ResponseEntity<Ingredient> response = ingredientController.deleteIngredient(ingredientId);

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void testDeleteIngredientNotFound() throws IOException { // deleteIngredient may throw IOException
        // Setup
        int ingredientId = 99;
        // when deleteIngredient is called return false, simulating failed deletion
        when(mockIngredientDAO.deleteIngredient(ingredientId)).thenReturn(false);

        // Invoke
        ResponseEntity<Ingredient> response = ingredientController.deleteIngredient(ingredientId);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    public void testDeleteIngredientHandleException() throws IOException { // deleteIngredient may throw IOException
        // Setup
        int ingredientId = 99;
        // When deleteIngredient is called on the Mock Ingredient DAO, throw an IOException
        doThrow(new IOException()).when(mockIngredientDAO).deleteIngredient(ingredientId);

        // Invoke
        ResponseEntity<Ingredient> response = ingredientController.deleteIngredient(ingredientId);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }
}
