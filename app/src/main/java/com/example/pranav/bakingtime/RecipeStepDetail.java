package com.example.pranav.bakingtime;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.pranav.bakingtime.utils.JSONUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class RecipeStepDetail extends AppCompatActivity implements RecipeStepFragment.OnButtonClickListener{

    private JSONObject mRecipeObject;
    int mPosition;
    int mNumSteps;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step_detail);

        Intent intent =getIntent();

        if(intent!=null) {

            String jsonString = intent.getStringExtra("jsonObject");
            mPosition = intent.getIntExtra("stepNumber", 1);


            try {
                mRecipeObject = new JSONObject(jsonString);
                mNumSteps = JSONUtils.numStepsRecipe(mRecipeObject);

            } catch (JSONException e) {
                e.printStackTrace();
            }


            RecipeStepFragment fragment = new RecipeStepFragment();
            fragment.setRecipeObject(mRecipeObject);
            fragment.setStepNumber(mPosition);
            fragment.mButtonCallBack = this;

            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .add(R.id.single_step_container, fragment)
                    .commit();

        }
    }

    @Override
    public void onButtonClick() {
        if(mPosition == mNumSteps){
            mPosition = 0;
        }
        mPosition = mPosition +1;


        RecipeStepFragment fragment = new RecipeStepFragment();
        fragment.setRecipeObject(mRecipeObject);
        fragment.setStepNumber(mPosition);
        fragment.mButtonCallBack = this;

        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .replace(R.id.single_step_container, fragment)
                .commit();

    }
}
