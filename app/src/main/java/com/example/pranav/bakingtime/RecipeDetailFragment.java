package com.example.pranav.bakingtime;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pranav.bakingtime.utils.JSONUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class RecipeDetailFragment extends Fragment implements RecipeDetailAdapter.OnStepClickListener {

    private static final String TAG = RecipeDetailFragment.class.getSimpleName();


    private JSONObject mRecipe;
    private boolean mTwoPane;
    OnClickPassListener mOnClickPassListener;

    interface OnClickPassListener{
        void onClick(int position);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mOnClickPassListener = (OnClickPassListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " should implement OnClickPassListener");
        }
    }

    public RecipeDetailFragment(){}

    public void setOnStepClickListener(OnClickPassListener listener){
        mOnClickPassListener = listener;
    }


    public void setRecipeObject(JSONObject recipe){
        mRecipe = recipe;
    }

    public void setTwoPane(boolean isTwoPane){ mTwoPane = isTwoPane;}



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(savedInstanceState!=null){

           String jsonString = savedInstanceState.getString(Recipe.JSON_STRING);
            try {
                mRecipe = new JSONObject(jsonString);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        String ingredients = null;
        String[] steps = null;
        String name = null;
        try {

            name = JSONUtils.getRecipesNameJSON(mRecipe);
            ingredients = JSONUtils.getIngredientsFromJSON(mRecipe);
            steps = JSONUtils.getStepsFromJSON(mRecipe);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        final View rootView = inflater.inflate(R.layout.fragment_step_list, container, false);


        TextView recipeNameTextView = rootView.findViewById(R.id.tv_recipe_name);

        recipeNameTextView.setText(name);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recipe_step_list_recyclerview);

        RecipeDetailAdapter mAdapter = new RecipeDetailAdapter(ingredients,steps,mTwoPane, this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(mAdapter);

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(Recipe.JSON_STRING, mRecipe.toString());
    }

    @Override
    public void onStepClick(int position) {

        mOnClickPassListener.onClick(position);

    }
}
