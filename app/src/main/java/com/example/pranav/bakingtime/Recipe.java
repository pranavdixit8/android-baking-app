package com.example.pranav.bakingtime;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.pranav.bakingtime.utils.JSONUtils;
import com.example.pranav.bakingtime.utils.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.net.URL;

public class Recipe extends AppCompatActivity implements LoaderManager.LoaderCallbacks<JSONArray> , RecipeAdapter.RecipeNameOnClickListener {

    private static final String TAG = Recipe.class.getSimpleName();

    public static final String JSON_STRING = "jsonObject";


    private static final int RECIPE_LOADER_ID = 1 ;
    private RecyclerView mRecylerView;
    private RecipeAdapter mRecipeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int loaderId = RECIPE_LOADER_ID;

        mRecylerView= findViewById(R.id.recipe_recyclerview);

        mRecipeAdapter = new RecipeAdapter(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        mRecylerView.setLayoutManager(linearLayoutManager);

        mRecylerView.setHasFixedSize(true);

        mRecylerView.setAdapter(mRecipeAdapter);

        LoaderManager loaderManager = getSupportLoaderManager();
        Loader<String> githubSearchLoader = loaderManager.getLoader(RECIPE_LOADER_ID);
        if (githubSearchLoader == null) {
            loaderManager.initLoader(RECIPE_LOADER_ID, null, this);
        } else {
            loaderManager.restartLoader(RECIPE_LOADER_ID, null, this);
        }

    }


    @Override
    public Loader<JSONArray> onCreateLoader(int i, Bundle bundle) {
        return new AsyncTaskLoader<JSONArray>(this) {



            JSONArray mRecipeArray = null;
            @Override
            protected void onStartLoading() {
                if(mRecipeArray!=null){
                    deliverResult(mRecipeArray);
                }
                else {
                    forceLoad();

                }
            }

            @Override
            public JSONArray loadInBackground() {
                URL url = NetworkUtils.buildURL();
                try {
                    String jsonResponse = NetworkUtils.getResponseFromAPI(url);
                    JSONArray recipeArray = JSONUtils.getRecipeDetails(jsonResponse);
                    return recipeArray;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
//check data for null
            @Override
            public void deliverResult(JSONArray data) {
                mRecipeArray=data;
                super.deliverResult(data);
            }
        };
    }


    @Override
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(RECIPE_LOADER_ID,null,this);
    }

    @Override
    public void onLoadFinished(Loader<JSONArray> loader, JSONArray data) {

        mRecipeAdapter.mRecipes = data;
        mRecipeAdapter.notifyDataSetChanged();

    }

    @Override
    public void onLoaderReset(Loader<JSONArray> loader) {

    }


    @Override
    public void onClick(JSONObject recipe) {
        String recipeString = recipe.toString();
        Intent intent = new Intent(Recipe.this, RecipeDetail.class);
        intent.putExtra(JSON_STRING, recipeString);

        startActivity(intent);

    }
}

