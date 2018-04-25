package com.example.pranav.bakingtime;

import android.content.Context;
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

        Log.d(TAG, "onCreate: ");

    }

    @Override
    public void onDataSetChanged() {

        Log.d(TAG, "onDataSetChanged: " );

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

        Log.d(TAG, "onDestroy: ");

    }

    @Override
    public int getCount() {
        Log.d(TAG, "getCount: ");
        if(mRecipes==null)return 0;
        return mRecipes.length();
    }

    @Override
    public RemoteViews getViewAt(int i) {

        Log.d(TAG, "getViewAt: ");

        JSONObject obj = null;
        String ingredients = null;
        String name = null;

        try {
            obj = mRecipes.getJSONObject(i);
            ingredients = JSONUtils.getIngredientNamesFromJSON(obj);
            name = obj.getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "getViewAt: " +name);
        Log.d(TAG, "getViewAt: "+ ingredients);

        RemoteViews views =  new RemoteViews(mContext.getPackageName(),R.layout.widget_list_item);

        views.setTextViewText(R.id.tv_recipe_name, name);
        views.setTextViewText(R.id.tv_ingredients, ingredients);

        return views;

    }

    @Override
    public RemoteViews getLoadingView() {
        Log.d(TAG, "getLoadingView: ");
        return null;
    }

    @Override
    public int getViewTypeCount() {
        Log.d(TAG, "getViewTypeCount: ");
        return 1;
    }

    @Override
    public long getItemId(int i) {
        Log.d(TAG, "getItemId: ");
        return i;
    }

    @Override
    public boolean hasStableIds() {

        Log.d(TAG, "hasStableIds: ");
        return true;
    }
}
