package com.example.pranav.bakingtime.utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONUtils {


    private static final String TAG = JSONUtils.class.getSimpleName();

    public static final String NAME_KEY = "name";
    private static final String STEPS_KEY = "steps";
    private static final String INGREDIENTS_KEY  = "ingredients";
    private static final String QUANTITY_KEY = "quantity";
    private static final String  MEASURE_KEY = "measure";
    private static final String INGREDIENT_KEY = "ingredient";
    private static final String SHORT_DESCRIPTION_KEY = "shortDescription";


    public static String[] getRecipesFromJSON(String recipeJsonResponse) throws JSONException {

        JSONArray recipeArray = new JSONArray(recipeJsonResponse);
        String[] recipes = new String[recipeArray.length()];
        for(int i =0 ; i< recipeArray.length();i++){
            JSONObject recipeObject = recipeArray.getJSONObject(i);
            recipes[i] = recipeObject.getString(NAME_KEY);
        }
        return recipes;

    }

    public static String getRecipesNameJSON(JSONObject obj) throws JSONException {
        if(obj==null){
            return null;
        }
        String name = obj.getString(NAME_KEY);
        return name;

    }

    public static String getStepInfoFromKey(JSONObject obj, int position, String key) throws JSONException {
//handling click on ingredients
        if(position == 0 ){
        return null;
        }
        String value = obj.getJSONArray(STEPS_KEY).getJSONObject(position-1).getString(key);
        return value;

    }

    public static int numStepsRecipe(JSONObject recipe) throws JSONException {
        JSONArray stepArray = recipe.getJSONArray(STEPS_KEY);
        return  stepArray.length();
    }


    public static JSONArray getRecipeDetails(String recipeJsonResponse) throws JSONException {

        JSONArray recipeArray = new JSONArray(recipeJsonResponse);
        return recipeArray;

    }

    public static String getIngredientsFromJSON(JSONObject recipe) throws JSONException {
        if(recipe==null){
            return null;
        }
        JSONArray ingredientArray= recipe.getJSONArray(INGREDIENTS_KEY);
        String ingredients = "";

        for(int i =0; i< ingredientArray.length();i++){
            JSONObject ingredientObj = ingredientArray.getJSONObject(i);
            int quantity = ingredientObj.getInt(QUANTITY_KEY);
            String measure = ingredientObj.getString(MEASURE_KEY);
            String ingredient = ingredientObj.getString(INGREDIENT_KEY);
            ingredients+= "  "+quantity + " " + measure + " " + ingredient + "\n\n";
        }
        return ingredients;
    }

    public static String getIngredientNamesFromJSON(JSONObject recipe) throws JSONException {
        JSONArray ingredientArray= recipe.getJSONArray(INGREDIENTS_KEY);
        String ingredients = "";
        for(int i =0; i< ingredientArray.length();i++){
            JSONObject ingredientObj = ingredientArray.getJSONObject(i);
            String ingredient = ingredientObj.getString(INGREDIENT_KEY);
            ingredients+= (i+1) + ". " + ingredient + "\n";
        }
        return ingredients;
    }

    public static String[] getStepsFromJSON(JSONObject recipe) throws JSONException {
        if(recipe==null){
            return null;
        }
        JSONArray stepArray= recipe.getJSONArray(STEPS_KEY);
        String[] steps = new String[stepArray.length()];
        for(int i =0; i< stepArray.length();i++){
            JSONObject stepObj = stepArray.getJSONObject(i);
            String step = stepObj.getString(SHORT_DESCRIPTION_KEY);
            steps[i] = step;
        }
        return steps;
    }




}
