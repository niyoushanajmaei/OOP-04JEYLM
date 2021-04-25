package diet;

public abstract class RawPackage implements NutritionalElement {
	
	String name;
	double calories;
	double proteins;
	double carbs;
	double fat;

	public RawPackage(String name,double calories,double proteins,double carbs,double fat) {
		this.name = name;
		this.calories = calories;
		this.proteins = proteins;
		this.carbs = carbs;
		this.fat = fat;
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


}
