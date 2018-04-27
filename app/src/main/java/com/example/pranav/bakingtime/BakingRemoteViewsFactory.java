package com.example.pranav.bakingtime;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.pranav.bakingtime.utils.JSONUtils;
import com.example.pranav.bakingtime.utils.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

public class BakingRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    
    private static final String TAG = BakingRemoteViewsFactory.class.getSimpleName();

    private JSONArray mRecipes;
    private Context mContext;

    public BakingRemoteViewsFactory(Context context){
        mContext = context;
    }

    @Override
    public void onCreate() {


    }

    @Override
    public void onDataSetChanged() {

        URL url = NetworkUtils.buildURL();
        try {
            String jsonResponse = NetworkUtils.getResponseFromAPI(url);
            JSONArray recipeArray = JSONUtils.getRecipeDetails(jsonResponse);
            mRecipes =  recipeArray;
        } catch (Exception e) {
            e.printStackTrace();
            mRecipes =  null;
        }

    }

    @Override
    public void onDestroy() {


    }

    @Override
    public int getCount() {
        if(mRecipes==null)return 0;
        return mRecipes.length();
    }

    @Override
    public RemoteViews getViewAt(int i) {

        JSONObject obj = null;
        String ingredients = null;
        String name = null;

        try {
            obj = mRecipes.getJSONObject(i);
            ingredients = JSONUtils.getIngredientNamesFromJSON(obj);
            name = JSONUtils.getRecipesNameJSON(obj);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RemoteViews views =  new RemoteViews(mContext.getPackageName(),R.layout.widget_list_item);
        views.setTextViewText(R.id.tv_recipe_name, name);
        views.setTextViewText(R.id.tv_ingredients, ingredients);

        Bundle b = new Bundle();
        Intent intent = new Intent();
        intent.putExtras(b);
        views.setOnClickFillInIntent(R.id.ll_widget_list_item,intent);
        return views;
    }

    @Override
    public RemoteViews getLoadingView() {

        return null;
    }

    @Override
    public int getViewTypeCount() {

        return 1;
    }

    @Override
    public long getItemId(int i) {

        return i;
    }

    @Override
    public boolean hasStableIds() {


        return true;
    }
}
