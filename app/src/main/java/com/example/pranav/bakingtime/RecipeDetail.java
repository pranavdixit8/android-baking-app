package com.example.pranav.bakingtime;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.pranav.bakingtime.utils.JSONUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class RecipeDetail extends AppCompatActivity implements RecipeDetailFragment.OnClickPassListener {


    private static final String TAG = RecipeDetail.class.getSimpleName();

    private JSONObject mRecipe;
    private int mStepNumber =1;
    boolean mTwoPane;
//    RecipeDetailAdapter.OnStepClickListener mPassingListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);


        if(findViewById(R.id.recipe_step_detail_linear_layout) !=null){
            mTwoPane = true;
            Log.d(TAG, "onCreate: " + mTwoPane);
        }else mTwoPane =false;


        Intent intent = getIntent();
        if(intent!=null && intent.hasExtra("jsonObject")){

            String recipeString = intent.getStringExtra("jsonObject");

            try {
                mRecipe = new JSONObject(recipeString);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.d(TAG, "onCreate: " + recipeString);

            if(savedInstanceState==null) {

                RecipeDetailFragment fragment = new RecipeDetailFragment();

                fragment.setRecipeObject(mRecipe);
                fragment.setOnStepClickListener(this);
                fragment.setTwoPane(mTwoPane);

                FragmentManager fragmentManager = getSupportFragmentManager();

                fragmentManager.beginTransaction()
                        .add(R.id.steps_container, fragment)
                        .commit();


                if (mTwoPane) {

                    RecipeStepFragment stepFragment = new RecipeStepFragment();
                    stepFragment.setRecipeObject(mRecipe);
                    stepFragment.setStepNumber(mStepNumber);
                    stepFragment.setTwoPane(true);


                    fragmentManager.beginTransaction()
                            .add(R.id.single_step_container, stepFragment)
                            .commit();


                }


            }
        }
    }



    @Override
    public void onClick(int position) {

        Log.d(TAG, "onClick: " + position);
        if(position<1)return;
        Log.d(TAG, "onClick2: " + position);
        if(mTwoPane){
            mStepNumber = position;

            RecipeStepFragment stepFragment = new RecipeStepFragment();
            stepFragment.setRecipeObject(mRecipe);
            stepFragment.setStepNumber(mStepNumber);
            stepFragment.setTwoPane(true);

            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .replace(R.id.single_step_container, stepFragment)
                    .commit();

        }else{

            Intent intent = new Intent(this, RecipeStepDetail.class);


            intent.putExtra("jsonObject", mRecipe.toString());
            intent.putExtra("stepNumber", position);


            startActivity(intent);
        }

    }

}
