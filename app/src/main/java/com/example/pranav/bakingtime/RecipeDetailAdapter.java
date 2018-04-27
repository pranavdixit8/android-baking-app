package com.example.pranav.bakingtime;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class RecipeDetailAdapter extends RecyclerView.Adapter<RecipeDetailAdapter.RecipeStepsViewHolder> {

    private static final String TAG = RecipeDetailAdapter.class.getSimpleName();


    private String mIngredients;
    private String[] mSteps;

    private boolean mTwoPane;

    OnStepClickListener mStepClickListener;
    int mClickedPosition =1 ;

    interface OnStepClickListener{

        void onStepClick(int position);
    }

    RecipeDetailAdapter(String ingredients, String[] steps,boolean isTwoPane, OnStepClickListener mPassingListener){
        mIngredients = ingredients;
        mSteps = steps;
        mTwoPane = isTwoPane;
        mStepClickListener = mPassingListener;
        notifyDataSetChanged();
    }

    @Override
    public RecipeStepsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view  = layoutInflater.inflate(R.layout.recipe_steps_list_item,parent,false);

        RecipeStepsViewHolder viewHolder = new RecipeStepsViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecipeStepsViewHolder holder, int position) {

        if(mTwoPane) {

            if (mClickedPosition == position)  {
                holder.itemView.setBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.colorAccent));
            } else {
                holder.itemView.setBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.holo_blue_light));
            }
        }

        if(position == 0) {
            holder.mIngredientHead.setText(R.string.ingredients);
            holder.mIngredientHead.setTextSize(25);
            holder.mIngredientHead.setPadding(20, 0, 0, 20);
            holder.mIngredientHead.setVisibility(View.VISIBLE);
            holder.mStepTextView.setText(mIngredients);
            holder.itemView.setBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.colorPrimary));
            holder.mStepTextView.setTextSize(15);
            holder.mCounterTextView.setVisibility(View.INVISIBLE);
            return;
        }

            holder.mIngredientHead.setVisibility(View.GONE);
            String step = mSteps[position - 1];
            holder.mStepTextView.setText(step);
            holder.mCounterTextView.setText("" + (position-1));
            holder.mCounterTextView.setVisibility(View.VISIBLE);

        if(position==1){
            holder.mCounterTextView.setVisibility(View.INVISIBLE);

        }


    }

    @Override
    public int getItemCount() {
        if(mSteps==null)return 0;
        else return mSteps.length;
    }

    public class RecipeStepsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mStepTextView;
        TextView mIngredientHead;
        TextView mCounterTextView;

        public RecipeStepsViewHolder(View itemView) {
            super(itemView);
            mStepTextView = itemView.findViewById(R.id.tv_recipe_step);
            mIngredientHead = itemView.findViewById(R.id.ingredients_head);
            mCounterTextView = itemView.findViewById(R.id.tv_step_counter);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            mClickedPosition = getAdapterPosition();
            mStepClickListener.onStepClick(getAdapterPosition());
            notifyDataSetChanged();
        }
    }
}
