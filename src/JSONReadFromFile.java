import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
*@author Danny Lui
*CUNY ID#: 23200057
*Project
*CSCI 363
*/

public class JSONReadFromFile {
	
	public ArrayList<Recipe> recipeListTrain = new ArrayList<Recipe>();
	public ArrayList<Recipe> recipeListTest = new ArrayList<Recipe>();	
	
	public JSONReadFromFile(String fileName) { /*parses json training and testing files*/
		JSONParser parser = new JSONParser();
		
		try {
			JSONArray a = (JSONArray) parser.parse(new FileReader(fileName));
			
			for (Object obj : a)
			{
				String cuisine = null;
				JSONObject jsonObject = (JSONObject) obj;
				
				long id = (long)jsonObject.get("id"); /*parse id*/

				if (fileName.equals("train.json")){
					cuisine = (String)jsonObject.get("cuisine"); /*parse cuisine*/
				}
			
				JSONArray ingredients = (JSONArray) jsonObject.get("ingredients"); /*parse ingredients*/
				
				ArrayList<String> tempIngredientsList = new ArrayList<String>();
				
				Iterator<String> iterator = ingredients.iterator();
				while(iterator.hasNext()) {
					
					String temp = iterator.next();
					tempIngredientsList.add(temp);
					if (fileName.equals("train.json"))
					{	
						if(!Driver.uniqCuisineList.contains(cuisine))
							Driver.uniqCuisineList.add(cuisine);
						Driver.cuisineList.add(cuisine);
						Driver.ingredientsList.add(temp);
					}
				}
				if (fileName.equals("train.json"))
					recipeListTrain.add(new Recipe(id, cuisine, tempIngredientsList)); /*training list recipes*/
				else
					recipeListTest.add(new Recipe(id, cuisine, tempIngredientsList)); /*testing list recipes*/
			}
		} catch (FileNotFoundException e) { 
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace(); 
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
