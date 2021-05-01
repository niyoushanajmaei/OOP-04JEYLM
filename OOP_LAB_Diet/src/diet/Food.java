package diet;

import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;

/**
 * Facade class for the diet management.
 * It allows defining and retrieving raw materials and products.
 *
 */
public class Food {
	
	private LinkedList<RawMaterial> rawMaterials;
	private LinkedList<Product> products;
	private LinkedList<Recipe> recipes;
	private LinkedList<Menu> menus;
	
	public Food() {
		rawMaterials = new LinkedList<>();
		products = new LinkedList<>();
		recipes = new LinkedList<>();
		menus = new LinkedList<>();
	}

	/**
	 * Define a new raw material.
	 * 
	 * The nutritional values are specified for a conventional 100g amount
	 * @param name 		unique name of the raw material
	 * @param calories	calories per 100g
	 * @param proteins	proteins per 100g
	 * @param carbs		carbs per 100g
	 * @param fat 		fats per 100g
	 */
	public void defineRawMaterial(String name,
									  double calories,
									  double proteins,
									  double carbs,
									  double fat){
		this.addRawMaterial(new RawMaterial(name,calories,proteins,carbs,fat));
	}
	
	private void addRawMaterial(RawMaterial rawMaterial) {
		rawMaterials.add(rawMaterial);
	}

	/**
	 * Retrieves the collection of all defined raw materials
	 * 
	 * @return collection of raw materials though the {@link NutritionalElement} interface
	 */
	public Collection<RawMaterial> rawMaterials(){
		rawMaterials.sort(Comparator.comparing(RawMaterial::getName));
		return rawMaterials;
	}
	
	/**
	 * Retrieves a specific raw material, given its name
	 * 
	 * @param name  name of the raw material
	 * 
	 * @return  a raw material though the {@link NutritionalElement} interface
	 */
	public NutritionalElement getRawMaterial(String name){
		for (RawMaterial e : rawMaterials) {
			if(name.equals(e.getName())) {
				return e;
			}
		}
		return null;
	}

	/**
	 * Define a new packaged product.
	 * The nutritional values are specified for a unit of the product
	 * 
	 * @param name 		unique name of the product
	 * @param calories	calories for a product unit
	 * @param proteins	proteins for a product unit
	 * @param carbs		carbs for a product unit
	 * @param fat 		fats for a product unit
	 */
	public void defineProduct(String name,
								  double calories,
								  double proteins,
								  double carbs,
								  double fat){
		this.addProduct(new Product(name,calories,proteins,carbs,fat));
	}
	
	private void addProduct(Product product) {
		products.add(product);	
	}

	/**
	 * Retrieves the collection of all defined products
	 * 
	 * @return collection of products though the {@link NutritionalElement} interface
	 */
	public Collection<Product> products(){
		products.sort(Comparator.comparing(Product::getName));
		return products;
	}
	
	/**
	 * Retrieves a specific product, given its name
	 * @param name  name of the product
	 * @return  a product though the {@link NutritionalElement} interface
	 */
	public NutritionalElement getProduct(String name){
		for (Product e : products) {
			if(name.equals(e.getName())) {
				return e;
			}
		}
		return null;
	}
	
	/**
	 * Creates a new recipe stored in this Food container.
	 *  
	 * @param name name of the recipe
	 * 
	 * @return the newly created Recipe object
	 */
	public Recipe createRecipe(String name) {
		Recipe recipe = new Recipe(name);
		this.addRecipe(recipe);
		return recipe;
	}
	
	private void addRecipe(Recipe recipe) {
		recipes.add(recipe);
		recipe.setRawMaterials(rawMaterials);
	}

	/**
	 * Retrieves the collection of all defined recipes
	 * 
	 * @return collection of recipes though the {@link NutritionalElement} interface
	 */
	public Collection<Recipe> recipes(){
		recipes.sort(Comparator.comparing(Recipe::getName));
		return recipes;
	}
	
	/**
	 * Retrieves a specific recipe, given its name
	 * 
	 * @param name  name of the recipe
	 * 
	 * @return  a recipe though the {@link NutritionalElement} interface
	 */
	public NutritionalElement getRecipe(String name){
		for (Recipe e : recipes) {
			if(name.equals(e.getName())) {
				return e;
			}
		}
		return null;
	}
	
	/**
	 * Creates a new menu
	 * 
	 * @param name name of the menu
	 * 
	 * @return the newly created menu
	 */
	public Menu createMenu(String name) {
		Menu menu = new Menu(name);
		menu.setReferenceRecipes(recipes);
		menu.setReferenceProducts(products);
		menus.add(menu);
		return menu;
	}

	public LinkedList<Menu> getMenus() {
		return menus;
	}
	
}
