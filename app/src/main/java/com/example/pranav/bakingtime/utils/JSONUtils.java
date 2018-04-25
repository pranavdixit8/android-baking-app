package com.example.pranav.bakingtime.utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONUtils {


    private static final String TAG = JSONUtils.class.getSimpleName();


    public static String[] getRecipesFromJSON(String recipeJsonResponse) throws JSONException {

        JSONArray recipeArray = new JSONArray(recipeJsonResponse);
        String[] recipes = new String[recipeArray.length()];

        for(int i =0 ; i< recipeArray.length();i++){
            JSONObject recipeObject = recipeArray.getJSONObject(i);
            recipes[i] = recipeObject.getString("name");
        }
        Log.d(TAG, "getRecipesFromJSON: " + recipes[0] + " to " + recipes[recipeArray.length()-1]);
        return recipes;

    }

    public static String getStepInfoFromKey(JSONObject obj, int position, String key) throws JSONException {
//handling click on ingredients
        if(position == 0 ){
        return null;
        }
        String value = obj.getJSONArray("steps").getJSONObject(position-1).getString(key);

        return value;

    }

    public static int numStepsRecipe(JSONObject recipe) throws JSONException {

        JSONArray stepArray = recipe.getJSONArray("steps");

        return  stepArray.length();
    }
    public static JSONArray getRecipeDetails(String recipeJsonResponse) throws JSONException {

        JSONArray recipeArray = new JSONArray(recipeJsonResponse);
        return recipeArray;

    }

    public static String getIngredientsFromJSON(JSONObject recipe) throws JSONException {

        JSONArray ingredientArray= recipe.getJSONArray("ingredients");

        String ingredients = "";

        for(int i =0; i< ingredientArray.length();i++){
            JSONObject ingredientObj = ingredientArray.getJSONObject(i);
            int quantity = ingredientObj.getInt("quantity");
            String measure = ingredientObj.getString("measure");
            String ingredient = ingredientObj.getString("ingredient");

            ingredients+= "  "+quantity + " " + measure + " " + ingredient + "\n\n";

        }

        return ingredients;
    }

    public static String getIngredientNamesFromJSON(JSONObject recipe) throws JSONException {

        JSONArray ingredientArray= recipe.getJSONArray("ingredients");

        String ingredients = "";

        for(int i =0; i< ingredientArray.length();i++){
            JSONObject ingredientObj = ingredientArray.getJSONObject(i);
            String ingredient = ingredientObj.getString("ingredient");

            ingredients+= (i+1) + ". " + ingredient + "\n";

        }

        return ingredients;
    }

    public static String[] getStepsFromJSON(JSONObject recipe) throws JSONException {

        JSONArray stepArray= recipe.getJSONArray("steps");

        String[] steps = new String[stepArray.length()];

        for(int i =0; i< stepArray.length();i++){
            JSONObject stepObj = stepArray.getJSONObject(i);
            String step = stepObj.getString("shortDescription");

            steps[i] = step;

        }
        return steps;
    }

    public static String[] getIngrediantsAndStepsFromJSON(JSONObject recipe) throws JSONException {

        JSONArray stepArray= recipe.getJSONArray("steps");

        String[] ingredientAndSteps = new String[stepArray.length() + 1 ];

        ingredientAndSteps[0] = getIngredientsFromJSON(recipe);
        int i=1;

        for( String str : getStepsFromJSON(recipe)){
            ingredientAndSteps[i] = str;
            i++;
        }

        return ingredientAndSteps;

    }


//
//    public static String[] getRecipeIngredientsFromJSON(String recipeJsonResponse) throws JSONException {
//
//        JSONArray recipeArray = new JSONArray(recipeJsonResponse);
//        String[] recipes = new String[recipeArray.length()];
//
//        for(int i =0 ; i< recipeArray.length();i++){
//            JSONObject recipeObject = recipeArray.getJSONObject(i);
//            recipes[i] = recipeObject.getString("name");
//        }
//        Log.d(TAG, "getRecipesFromJSON: " + recipes[0] + " to " + recipes[recipeArray.length()-1]);
//        return recipes;
//
//    }
//
//    public static String[] getRecipesFromJSON(String recipeJsonResponse) throws JSONException {
//
//        JSONArray recipeArray = new JSONArray(recipeJsonResponse);
//        String[] recipes = new String[recipeArray.length()];
//
//        for(int i =0 ; i< recipeArray.length();i++){
//            JSONObject recipeObject = recipeArray.getJSONObject(i);
//            recipes[i] = recipeObject.getString("name");
//        }
//        Log.d(TAG, "getRecipesFromJSON: " + recipes[0] + " to " + recipes[recipeArray.length()-1]);
//        return recipes;
//
//    }



}
