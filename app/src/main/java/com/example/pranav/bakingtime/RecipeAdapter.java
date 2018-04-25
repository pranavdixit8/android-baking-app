package com.example.pranav.bakingtime;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.pranav.bakingtime.R.id.cv_recipe;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>  {

    private static final String TAG = RecipeAdapter.class.getSimpleName();

    private RecipeNameOnClickListener mOnClickListener;
    JSONArray mRecipes;
//    Context mContext;
//
    RecipeAdapter(RecipeNameOnClickListener listener){

        mOnClickListener = listener;
    }

    interface RecipeNameOnClickListener{
        void onClick(JSONObject recipe);
    }



    public void setRecipeData(JSONArray recipes){

        mRecipes = recipes;
    }


    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        int layoutResource = R.layout.recipe_list_item;
        boolean attachToParent =false;

        View view = layoutInflater.inflate(layoutResource,parent,attachToParent);

         RecipeViewHolder viewHolder = new RecipeViewHolder(view);

         return viewHolder;

    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {

        try {
            JSONObject recipeObject = mRecipes.getJSONObject(position);
            String recipeName = recipeObject.getString("name");
            Log.d(TAG, "onBindViewHolder: " + recipeName);
            holder.mRecipeTextView.setText(recipeName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {

        Log.d(TAG, "getItemCount: " + mRecipes);

        if(mRecipes==null)return 0;
        else return mRecipes.length();

    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        CardView mCardView;
        TextView mRecipeTextView;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            mCardView = itemView.findViewById(R.id.cv_recipe);
            mRecipeTextView = itemView.findViewById(R.id.tv_recipe_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            try {
                JSONObject recipe = mRecipes.getJSONObject(position);
                mOnClickListener.onClick(recipe);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
