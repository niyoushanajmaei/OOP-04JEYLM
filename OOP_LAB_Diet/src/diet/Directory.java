package diet;

import java.util.Comparator;
import java.util.LinkedList;

public final class Directory {
	
	private static Directory root;
	private LinkedList<RawMaterial> rawMaterials;
	private LinkedList<Product> products;
	LinkedList<Recipe> recipes;
	
	private Directory(){
		rawMaterials = new LinkedList<>();
		products = new LinkedList<>();
		recipes = new LinkedList<>();
	}
	
	public static Directory getInstance() {
		if (root==null) {
			root = new Directory();
			return root;
		}else {
			return root;
		}
	}
	
	public void addRawMaterial(RawMaterial material) {
		if (!rawMaterials.contains(material)){
			rawMaterials.add(material);
		}
	}
	
	public void addProduct(Product product) {
		if (!products.contains(product)) {
			products.add(product);
		}
	}

	public void addRecipe(Recipe recipe) {
		if(!recipes.contains(recipe)) {
			recipes.add(recipe);
		}
	}
	
	public LinkedList<RawMaterial> getRawMaterials() {
		rawMaterials.sort(Comparator.comparing(RawMaterial::getName));
		return rawMaterials;
	}
	
	public LinkedList<Product> getProducts(){
		products.sort(Comparator.comparing(Product::getName));
		return products;
	}
	
	public LinkedList<Recipe> getRecipes(){
		recipes.sort(Comparator.comparing(Recipe::getName));
		return recipes;
	}
}
