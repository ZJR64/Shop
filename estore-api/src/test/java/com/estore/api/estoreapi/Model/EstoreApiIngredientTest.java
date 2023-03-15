package com.estore.api.estoreapi.Model;
import static org.junit.jupiter.api.Assertions.assertEquals;


import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.estore.api.estoreapi.model.Ingredient;


/**
 * The unit test suite for the Ingredient class
 * 
 * @author AADITH CHARUGUNDLA (APC3277)
 */
@Tag("Model-tier")
@SpringBootTest
class EstoreApiIngredientTest {

	@Test
	public void testCtor() {
		// Setup
		int expected_id = 69;
		String expected_name = "Deez Nuts";
		String expected_type = "Bean";
		String expected_description = "Gottem";
		double expected_price = 0.69;
		double expected_volume = 420.69;

		// Invoke
		Ingredient ingredient = new Ingredient(expected_id, expected_name, expected_type, expected_description, expected_price,
				expected_volume);

		// Analyze
		assertEquals(expected_id, ingredient.getId());
		assertEquals(expected_name, ingredient.getName());
		assertEquals(expected_type, ingredient.getType());
		assertEquals(expected_description, ingredient.getDescription());
		assertEquals(expected_price, ingredient.getPrice());
		assertEquals(expected_volume, ingredient.getVolume());

	}

	@Test
	public void testName() {
		// Setup
		int id = 69;
		String name = "Deez Nuts";
		String type = "Bean";
		String description = "Gottem";
		double price = 0.69;
		double volume = 420.69;
		Ingredient ingredient = new Ingredient(id, name, type, description, price, volume);

		String expected_name = "Balls";

		// Invoke
		ingredient.setName(expected_name);

		// Analyze
		assertEquals(expected_name, ingredient.getName());
	}

	@Test
	public void testType() {
		// Setup
		int id = 69;
		String name = "Deez Nuts";
		String type = "Bean";
		double price = 0.69;
		String description = "Gottem";
		double volume = 420.69;
		Ingredient ingredient = new Ingredient(id, name, type, description, price, volume);

		String expected_type = "Not Beans?";

		// Invoke
		ingredient.setType(expected_type);

		// Analyze
		assertEquals(expected_type, ingredient.getType());
	}

	@Test
	public void testDescription() {
		// Setup
		int id = 69;
		String name = "Deez Nuts";
		String type = "Bean";
		double price = 0.69;
		String description = "Gottem";
		double volume = 420.69;
		Ingredient ingredient = new Ingredient(id, name, type, description, price, volume);

		String expected_description = "NAH";

		// Invoke
		ingredient.setDescription(expected_description);

		// Analyze
		assertEquals(expected_description, ingredient.getDescription());
	}

	@Test
	public void testPrice() {
		// Setup
		int id = 69;
		String name = "Deez Nuts";
		String type = "Bean";
		String description = "Gottem";
		double price = 0.69;
		double volume = 420.69;
		Ingredient ingredient = new Ingredient(id, name, type, description, price, volume);

		double expected_price = 0.21;

		// Invoke
		ingredient.setPrice(expected_price);

		// Analyze
		assertEquals(expected_price, ingredient.getPrice());
	}

	@Test
	public void testVolume() {
		// Setup
		int id = 69;
		String name = "Deez Nuts";
		String type = "Bean";
		String description = "Gottem";
		double price = 0.69;
		double volume = 420.69;
		Ingredient ingredient = new Ingredient(id, name, type, description, price, volume);

		double expected_volume = 69.420;

		// Invoke
		ingredient.setVolume(expected_volume);

		// Analyze
		assertEquals(expected_volume, ingredient.getVolume());
	}

	@Test
	public void testToString() {
		// Setup
		int id = 69;
		String name = "Deez Nuts";
		String type = "Bean";
		String description = "Gottem";
		double price = 0.69;
		double volume = 420.69;
		String expected_string = String.format(Ingredient.getSTRING_FORMAT(), id, name, type, price, volume);
		Ingredient ingredient = new Ingredient(id, name, type, description, price, volume);

		// Invoke
		String actual_string = ingredient.toString();

		// Analyze
		assertEquals(expected_string, actual_string);
	}
}
