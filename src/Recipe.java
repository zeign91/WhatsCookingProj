import java.util.ArrayList;

/**
*@author Danny Lui
*CUNY ID#: 23200057
*Project
*CSCI 363
*/

public class Recipe {

	private double max = 0;
	private long id;
	private String cuisine;
	private ArrayList<String> ingredients;
	
	public Recipe(long id, String cuisine, ArrayList<String> ingredients){
		this.id = id;
		this.cuisine = cuisine;
		this.ingredients = ingredients;
	}
	
	public long getId() {
		return id;
	}
	public String getCuisine() {
		return cuisine;
	}
	public ArrayList<String> getIngredients() {
		return ingredients;
	}
	
	public void setProbability(String possibleCuisine, double prob) { /*cuisine determined by the highest prob*/
		if (prob > max) {
			max = prob;
			cuisine = possibleCuisine;
		}
	}
	
	public String toString() { /*string representation of recipe object*/
		String ingredientString = "";
		for (int i = 0; i < ingredients.size(); i++)
			ingredientString = ingredientString + ingredients.get(i) + "\n";
		return "ID: " + id + "\nCuisine: " + cuisine + "\nIngredients: \n" + ingredientString;
	}
}
