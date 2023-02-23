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

import com.estore.api.estoreapi.persistence.IngredientDAO;
import com.estore.api.estoreapi.model.Ingredient;

/**
 * Handles the REST API requests for the Ingredient resource
 * <p>
 * {@literal @}RestController Spring annotation identifies this class as a REST
 * API
 * method handler to the Spring framework
 * 
 * @author Zak Rutherford
 */

@RestController
@RequestMapping("ingredients")
public class IngredientController {
    private static final Logger LOG = Logger.getLogger(IngredientController.class.getName());
    private IngredientDAO ingredientDao;

    /**
     * Creates a REST API controller to reponds to requests
     * 
     * @param ingredientDao The {@link IngredientDAO Ingredient Data Access Object} to
     *                   perform CRUD operations
     *                   <br>
     *                   This dependency is injected by the Spring Framework
     */
    public IngredientController(IngredientDAO ingredientDao) {
        this.ingredientDao = ingredientDao;
    }

    /**
     * Responds to the GET request for a {@linkplain Ingredient ingredient} for the given
     * id
     * 
     * @param id The id used to locate the {@link Ingredient ingredient}
     * 
     * @return ResponseEntity with {@link Ingredient ingredient} object and HTTP status of
     *         OK if found<br>
     *         ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/{id}")
    public ResponseEntity<Ingredient> getIngredient(@PathVariable int id) {
        LOG.info("GET /ingredient/" + id);
        try {
            Ingredient ingredient = ingredientDao.getIngredient(id);
            if (ingredient != null)
                return new ResponseEntity<Ingredient>(ingredient, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Creates a {@linkplain Ingredient ingredient} with the provided ingredient object
     *
     * @param ingredient - The {@link Ingredient ingredient} to create
     *
     * @return ResponseEntity with created {@link Ingredient ingredient} object and HTTP
     *         status of CREATED<br>
     *         ResponseEntity with HTTP status of CONFLICT if {@link Ingredient
     *         ingredient} object already exists<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("")
    public ResponseEntity<Ingredient> createIngredient(@RequestBody Ingredient ingredient) {
        LOG.info("POST /ingredients " + ingredient);
        try {
            Ingredient newIngredient = ingredientDao.createIngredient(ingredient);
            if (newIngredient != null) {
                return new ResponseEntity<Ingredient>(newIngredient, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all {@linkplain Ingredient ingredients}
     * 
     * @return ResponseEntity with array of {@link Ingredient ingredient} objects (may be
     *         empty) and
     *         HTTP status of OK<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("")
    public ResponseEntity<Ingredient[]> getIngredients() {
        LOG.info("GET /ingredients");

        try {
            Ingredient[] ingredients = ingredientDao.getIngredients();
            return new ResponseEntity<Ingredient[]>(ingredients, HttpStatus.OK);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all {@linkplain Ingredient ingredients} whose name
     * contains
     * the text in name
     * 
     * @param name The name parameter which contains the text used to find the
     *             {@link Ingredient ingredients}
     * 
     * @return ResponseEntity with array of {@link Ingredient hero} objects (may be
     *         empty) and
     *         HTTP status of OK<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     *         <p>
     *         Example: Find all ingredients that contain the text "ma"
     *         GET http://localhost:8080/ingredients/?name=ma
     */
    @GetMapping("/")
    public ResponseEntity<Ingredient[]> searchIngredients(@RequestParam String name) {
        LOG.info("GET /ingredients/?name=" + name);

        try {
            Ingredient[] ingredients = ingredientDao.findIngredients(name);
            if (ingredients != null) {
                return new ResponseEntity<Ingredient[]>(ingredients, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Updates the {@linkplain Ingredient ingredient} with the provided
     * {@linkplain Ingredient ingredient} object, if it exists
     * 
     * @param Ingredient ingredient The {@link Ingredient ingredient} to update
     * 
     * @return ResponseEntity with updated {@link Ingredient ingredient} object and HTTP
     *         status of OK if updated<br>
     *         ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PutMapping("")
    public ResponseEntity<Ingredient> updateIngredient(@RequestBody Ingredient ingredient) {
        LOG.info("PUT /ingredients " + ingredient);

        // Replace below with your implementation
        try {
            Ingredient ingredientCheck = ingredientDao.updateIngredient(ingredient);
            if (ingredientCheck != null) {
                return new ResponseEntity<Ingredient>(ingredientCheck, HttpStatus.OK);
            }
            return new ResponseEntity<Ingredient>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes a {@linkplain Ingredient ingredient} with the given id
     * 
     * @param id The id of the {@link Ingredient ingredient} to deleted
     * 
     * @return ResponseEntity HTTP status of OK if deleted<br>
     *         ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Ingredient> deleteIngredient(@PathVariable int id) {
        LOG.info("DELETE /ingredients/" + id);

        // Replace below with your implementation
        try {
            boolean ingredientCheck = ingredientDao.deleteIngredient(id);
            if (ingredientCheck) {
                return new ResponseEntity<Ingredient>(HttpStatus.OK);
            }
            return new ResponseEntity<Ingredient>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
