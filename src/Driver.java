import java.util.ArrayList;

/**
*@author Danny Lui
*CUNY ID#: 23200057
*Project
*CSCI 363
*/

public class Driver {
	
	public static ArrayList<String> uniqCuisineList = new ArrayList<String>();
	public static ArrayList<String> cuisineList = new ArrayList<String>();
	public static ArrayList<String> ingredientsList = new ArrayList<String>();
	public static int size = 0;
	
	public static void main(String[] args) 
	{
		/*parse and initialize training and testing data sets*/
		JSONReadFromFile trainingData = new JSONReadFromFile("train.json");
		JSONReadFromFile testingData = new JSONReadFromFile("test.json");
		size = cuisineList.size();
		
		print("~Testing Data Solution to Empty 'Cuisine'~\n");
		
		//iterate through the test set and calculate cuisine probabilties
		for (int i = 0; i < testingData.recipeListTest.size(); i++)
			calcCuisine(testingData.recipeListTest.get(i));
	}
	
	public static void calcCuisine(Recipe r) 
	{
		double probCuisine = 1.0;
		
		/*beginning with cuisine x*/
		for (int x = 0; x < uniqCuisineList.size(); x++)
		{	/*ingredient y*/
			for (int y = 0; y < r.getIngredients().size(); y++)
			{
				int cuisineIndex = x;
				String targetCuisine = uniqCuisineList.get(cuisineIndex);
				//print("Target Cuisine: " + targetCuisine);
				
				int ingredientsIndex = y;
				String targetIngredient = r.getIngredients().get(ingredientsIndex);
				//print("Target Ingredient: " + targetIngredient);
				
				double probAns;	/*prob of x cuisine given y ingredient*/
				
				double probCuisine1; /*prob of cuisine and prob of !cuisine*/
				double probCuisine0;
				
				double probI0C0; /*prob of ingredient given cuisine*/
				double probI0C1;
				double probI1C0;
				double probI1C1;
				
				int numCuisine = 0;	/*total num of this cuisine*/
				int numIngredientC1 = 0; /*total num of this ingredient given cuisine*/
				int numIngredientC0 = 0; /*total num of this ingredient given !cuisine*/
				
				for(int i = 0; i < size; i++)
				{
					if (uniqCuisineList.get(cuisineIndex).equals(cuisineList.get(i))) 
						numCuisine++;
					if (r.getIngredients().get(ingredientsIndex).equals(ingredientsList.get(i)) && 
							uniqCuisineList.get(cuisineIndex).equals(cuisineList.get(i))) 
						numIngredientC1++; /*counts for this ingredient AND this cuisine*/
					if (r.getIngredients().get(ingredientsIndex).equals(ingredientsList.get(i)) &&
							!uniqCuisineList.get(cuisineIndex).equals(cuisineList.get(i)))
						numIngredientC0++; /*counts for this ingredient AND NOT this cuisine*/	
				}

				probCuisine1 = (double) numCuisine  / (double) size;
				probCuisine0 = 1.0 - probCuisine1;
				
				probI1C1 = (double) numIngredientC1 / (double) numCuisine;
				probI1C0 = (double) numIngredientC0 / ((double) size - (double) numCuisine);
				
				double denom = ((probI1C1 * probCuisine1) + (probI1C0 * probCuisine0));
				if (denom == 0.0) //prevent division by 0
					denom = 0.01;
				probAns = (probI1C1 * probCuisine1) / (denom); 
				
				//printSubData(targetCuisine, targetIngredient, probCuisine1, probCuisine0, probAns, probI1C0, probI1C1, numIngredientC1);
				
				if (probAns == 0.0) //prevent null cuisines 
					probAns = 0.01;
				probCuisine = probCuisine * probAns;	
			
			}
			//print("Probability of " + uniqCuisineList.get(x) + " is : " + probCuisine);
			r.setProbability(uniqCuisineList.get(x), probCuisine);	
			probCuisine = 1;	
		}
		
		print(r);
	}
	
	//prints the all the probabilities of every ingredient for every cuisine
	public static void printSubData(String targetCuisine, String targetIngredient, double probCuisine1, double probCuisine0, 
			double probAns, double probI1C0, double probI1C1, double numIngredientC1) {
		
		//print("P(" + targetCuisine + "): " + probCuisine1);
		//print("P(!" + targetCuisine + "): " + probCuisine0);
		
		//print("P(" + targetIngredient + " | " + targetCuisine + "): " + probI1C1);
		//print("P(" + targetIngredient + " | !" + targetCuisine + "): " + probI1C0);
		
		//print("Num " + targetIngredient + " used in " + targetCuisine + ": " + numIngredientC1);
		
		print("P(" + targetCuisine + ") uses " + targetIngredient + " is " + probAns);
	}
	
	public static void print(Object line) {
		System.out.println(line);
	}
}
