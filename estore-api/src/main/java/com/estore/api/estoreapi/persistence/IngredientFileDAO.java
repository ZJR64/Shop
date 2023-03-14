package com.estore.api.estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.estore.api.estoreapi.model.Ingredient;

/**
 * Implements the functionality for JSON file-based peristance for Ingredients
 * 
 * {@literal @}Component Spring annotation instantiates a single instance of
 * this
 * class and injects the instance into other classes as needed
 * 
 * @author Zak Rutherford
 */
@Component
public class IngredientFileDAO implements IngredientDAO {
    private static final Logger LOG = Logger.getLogger(IngredientFileDAO.class.getName());
    public Map<Integer, Ingredient> ingredients; // Provides a local cache of the Ingredient objects
                                    // so that we don't need to read from the file
                                    // each time
    private ObjectMapper objectMapper; // Provides conversion between Ingredients
                                       // objects and JSON text format written
                                       // to the file
    private static int currentID; // The lates ID to be assigned to an ingredient
    private static ArrayList<Integer> deletedIDs; // arraylist of all deleted ids
    private String filename; // Filename to read from and write to

    /**
     * Creates a Ingredient File Data Access Object
     * 
     * @param filename     Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization
     *                     and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public IngredientFileDAO(@Value("${ingredients.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load(); // load the ingredients from the file
    }

    /**
     * Generates the next id for a new {@linkplain Ingredient ingredient}
     * 
     * @return The an id
     */
    private synchronized static int nextId() {
        if (deletedIDs.size() > 0) {
            return deletedIDs.remove(0);
        }
        else {
            currentID++;
            return currentID;
        }
    }

    /**
     * Generates an array of {@linkplain Ingredient ingredients} from the tree map
     * 
     * @return The array of {@link Ingredient ingredients}, may be empty
     */
    private Ingredient[] getIngredientsArray() {
        return getIngredientsArray(null);
    }

    /**
     * Generates an array of {@linkplain Ingredient ingredients} from the tree map for any
     * {@linkplain Ingredient ingredients} that contains the text specified by
     * containsText
     * <br>
     * If containsText is null, the array contains all of the {@linkplain Ingredient
     * ingredients}
     * in the tree map
     * 
     * @return The array of {@link Ingredient ingredients}, may be empty
     */
    private Ingredient[] getIngredientsArray(String containsText) { // if containsText == null, no filter
        ArrayList<Ingredient> ingredientArrayList = new ArrayList<>();

        for (Ingredient ingredient : ingredients.values()) {
            if (containsText == null || ingredient.getName().contains(containsText)) {
                ingredientArrayList.add(ingredient);
            }
        }

        Ingredient[] ingredientArray = new Ingredient[ingredientArrayList.size()];
        ingredientArrayList.toArray(ingredientArray);
        return ingredientArray;
    }

    /**
     * Saves the {@linkplain Ingredient ingredients} from the map into the file as an
     * array of JSON objects
     * 
     * @return true if the {@link Ingredient ingredients} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        Ingredient[] ingredientArray = getIngredientsArray();

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename), ingredientArray);
        return true;
    }

    /**
     * Loads {@linkplain Ingredient ingredients} from the JSON file into the map
     * <br>
     * Also sets next id to one more than the greatest id found in the file
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        ingredients = new TreeMap<>();
        currentID = 0;
        deletedIDs = new ArrayList<Integer>();

        // Deserializes the JSON objects from the file into an array of ingredients
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        Ingredient[] ingredientArray = objectMapper.readValue(new File(filename), Ingredient[].class);

        // Add each ingredient to the tree map and keep track of the greatest id
        int maxID = 0;
        for (Ingredient ingredient : ingredientArray) {
            ingredients.put(ingredient.getId(), ingredient);
            if (ingredient.getId() > maxID)
                maxID = ingredient.getId();
        }

        //assign the value.
        currentID = maxID;

        //find the missing ids to fil in the deleted id list
        for (int i = 1; i <= maxID; i++) {
            deletedIDs.add(i);
        }

        for (Ingredient ingredient : ingredientArray) {
            deletedIDs.remove(deletedIDs.indexOf(ingredient.getId()));
        }

        return true;
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public Ingredient[] getIngredients() {
        synchronized (ingredients) {
            return getIngredientsArray();
        }
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public Ingredient[] findIngredients(String containsText) {
        synchronized (ingredients) {
            String searchLowerCase = containsText.toLowerCase();
            List<Ingredient> matches = new ArrayList<>();
            for (Ingredient ingredient : getIngredientsArray()) {
                if (ingredient.getName().toLowerCase().contains(searchLowerCase)) {
                    matches.add(ingredient);
                }
            }
            return matches.toArray(new Ingredient[0]);
        }
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public Ingredient getIngredient(int id) {
        synchronized (ingredients) {
            if (ingredients.containsKey(id))
                return ingredients.get(id);
            else
                return null;
        }
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public Ingredient createIngredient(Ingredient ingredient) throws IOException {
        synchronized (ingredients) {
            // We create a new ingredient object because the id field is immutable
            // and we need to assign the next unique id
            Ingredient newIngredient = new Ingredient(nextId(), ingredient.getName(), ingredient.getType(), ingredient.getDescription(),
            ingredient.getPrice(), ingredient.getVolume());
            ingredients.put(newIngredient.getId(), newIngredient);
            save(); // may throw an IOException
            return newIngredient;
        }
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public Ingredient updateIngredient(Ingredient ingredient) throws IOException {
        synchronized (ingredients) {
            if (ingredients.containsKey(ingredient.getId()) == false)
                return null; // ingredient does not exist

                ingredients.put(ingredient.getId(), ingredient);
            save(); // may throw an IOException
            return ingredient;
        }
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public boolean deleteIngredient(int id) throws IOException {
        synchronized (ingredients) {
            if (ingredients.containsKey(id)) {
                ingredients.remove(id);
                deletedIDs.add(id);
                return save();
            } else
                return false;
        }
    }
}
