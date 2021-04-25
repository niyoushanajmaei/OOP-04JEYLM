package diet;

public class Product extends RawPackage{

	public Product(String name, double calories, double proteins, double carbs, double fat) {
		super(name, calories, proteins, carbs, fat);
	}

	@Override
	public boolean per100g() {
		return false;
	}

}
