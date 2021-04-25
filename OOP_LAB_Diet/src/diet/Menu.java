package diet;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Represents a complete menu.
 * 
 * It can be made up of both packaged products and servings of given recipes.
 *
 */
public class Menu implements NutritionalElement {
	
	Directory root = Directory.getInstance();
	
	Map<String,Double> recipes = new HashMap<>();
	LinkedList<String> products = new LinkedList<>();
	String name;
	double calories;
	double proteins;
	double carbs;
	double fat;
	
	public Menu(String name) {
		this.name = name;
	}

	/**
	 * Adds a given serving size of a recipe.
	 * 
	 * The recipe is a name of a recipe defined in the
	 * {@Link Food} in which this menu has been defined.
	 * 
	 * @param recipe the name of the recipe to be used as ingredient
	 * @param quantity the amount in grams of the recipe to be used
	 * @return the same Menu to allow method chaining
	 */
	public Menu addRecipe(String recipe, double quantity) {
		recipes.put(recipe, quantity);
		for (Recipe r : root.getRecipes()) {
			System.out.println(root.getRecipes().size());
			if (r.getName().equals(recipe)) {
				calories += r.getCalories() *quantity/100;
				//System.out.println(r.getCalories());
				proteins += r.getProteins() *quantity/100;
				carbs += r.getCarbs() *quantity/100;
				fat += r.getFat() * quantity/100;
			}
		}
		return this;
	}

	/**
	 * Adds a unit of a packaged product.
	 * The product is a name of a product defined in the
	 * {@Link Food} in which this menu has been defined.
	 * 
	 * @param product the name of the product to be used as ingredient
	 * @return the same Menu to allow method chaining
	 */
	public Menu addProduct(String product) {
		products.add(product);
		for(Product p : root.getProducts()) {
			if (p.getName().equals(product)) {
				calories += p.getCalories() ;
				//System.out.println(p.getCalories());
				proteins += p.getProteins() ;
				carbs += p.getCarbs();
				fat += p.getFat();
			}
		}
		return this;
	}

	/**
	 * Name of the menu
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * Total KCal in the menu
	 */
	@Override
	public double getCalories() {
		return calories;
	}

	/**
	 * Total proteins in the menu
	 */
	@Override
	public double getProteins() {
		return proteins;
	}

	/**
	 * Total carbs in the menu
	 */
	@Override
	public double getCarbs() {
		return carbs;
	}

	/**
	 * Total fats in the menu
	 */
	@Override
	public double getFat() {
		return fat;
	}

	/**
	 * Indicates whether the nutritional values returned by the other methods
	 * refer to a conventional 100g quantity of nutritional element,
	 * or to a unit of element.
	 * 
	 * For the {@link Menu} class it must always return {@code false}:
	 * nutritional values are provided for the whole menu.
	 * 
	 * @return boolean 	indicator
	 */
	@Override
	public boolean per100g() {
		// nutritional values are provided for the whole menu.
		return false;
	}
}
