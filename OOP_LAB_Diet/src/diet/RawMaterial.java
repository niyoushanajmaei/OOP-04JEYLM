package diet;

public class RawMaterial extends RawPackage {
	
	public RawMaterial(String name, double calories, double proteins, double carbs, double fat) {
		super(name, calories, proteins, carbs, fat);
	}

	@Override
	public boolean per100g() {
		return true;
	}

}
