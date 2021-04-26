package diet;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a recipe of the diet.
 * 
 * A recipe consists of a a set of ingredients that are given amounts of raw materials.
 * The overall nutritional values of a recipe can be computed
 * on the basis of the ingredients' values and are expressed per 100g
 * 
 *
 */
public class Recipe implements NutritionalElement {
	
	Directory root = Directory.getInstance();
    
	String name;
	Map<String,Double> materials = new HashMap<>();
	double calories;
	double proteins;
	double carbs;
	double fat;
	int q;

	public Recipe(String name) {
		this.name = name;
		root.addRecipe(this);
	}

	/**
	 * Adds a given quantity of an ingredient to the recipe.
	 * The ingredient is a raw material.
	 * 
	 * @param material the name of the raw material to be used as ingredient
	 * @param quantity the amount in grams of the raw material to be used
	 * @return the same Recipe object, it allows method chaining.
	 */
	public Recipe addIngredient(String material, double quantity) {
		materials.put(material,quantity);
		for (RawMaterial e : root.getRawMaterials()) {
			if (e.getName().equals(material)) {
				calories = (calories*q + e.getCalories()*quantity)/(q+quantity);
				//System.out.println(calories);
				proteins = (proteins*q + e.getProteins()*quantity)/(q+quantity);
				carbs = (carbs*q + e.getCarbs()*quantity)/(q+quantity);
				fat = (fat*q + e.getFat()*quantity)/(q+quantity);;
			}
		}
		for (Recipe r : root.getRecipes()) {
			if (r.getName().equals(this.name)) {
				r = this;
			}
		}
		q+=quantity;
		return this;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public double getCalories() {
		return calories;
	}

	@Override
	public double getProteins() {
		return proteins;
	}

	@Override
	public double getCarbs() {
		return carbs;
	}

	@Override
	public double getFat() {
		return fat;
	}

	/**
	 * Indicates whether the nutritional values returned by the other methods
	 * refer to a conventional 100g quantity of nutritional element,
	 * or to a unit of element.
	 * 
	 * For the {@link Recipe} class it must always return {@code true}:
	 * a recipe expresses nutritional values per 100g
	 * 
	 * @return boolean indicator
	 */
	@Override
	public boolean per100g() {
		return true;
	}
	
	
	/**
	 * Returns the ingredients composing the recipe.
	 * 
	 * A string that contains all the ingredients, one per per line, 
	 * using the following format:
	 * {@code "Material : ###.#"} where <i>Material</i> is the name of the 
	 * raw material and <i>###.#</i> is the relative quantity. 
	 * 
	 * Lines are all terminated with character {@code '\n'} and the ingredients 
	 * must appear in the same order they have been added to the recipe.
	 */
	@Override
	public String toString() {
		String res = "";
		for(String k : materials.keySet()) {
			res += k+ " : " + truncate(materials.get(k)) + "\n";
		}
		return res;
	}

	private Double truncate(Double d) {
		int intValue = d.intValue();
		Double floating = d-intValue;
		int perc = ((Double)(floating*10)).intValue();
		return (double) (intValue + perc/10);
	}
	
}
