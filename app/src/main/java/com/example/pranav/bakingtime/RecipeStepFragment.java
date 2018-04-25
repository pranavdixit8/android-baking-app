package com.example.pranav.bakingtime;


import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.pranav.bakingtime.utils.JSONUtils;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;

public class RecipeStepFragment extends android.support.v4.app.Fragment {

    private static final String TAG = RecipeStepFragment.class.getSimpleName();

    int mStepNumber;
    private JSONObject mRecipe;
    private boolean mTwoPane;
    private SimpleExoPlayer mPlayer;
    private SimpleExoPlayerView mPlayerView;

    OnButtonClickListener mButtonCallBack;

    interface OnButtonClickListener{
        void onButtonClick();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_step_detail, container, false);

        if(savedInstanceState!=null){
            String jsonString = savedInstanceState.getString("jsonString");
            mStepNumber = savedInstanceState.getInt("stepNumber");
            try {
                mRecipe = new JSONObject(jsonString);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mTwoPane = savedInstanceState.getBoolean("isTwoPane");
        }

        mPlayerView = rootView.findViewById(R.id.sepv_media_player);
        TextView stepTextView = rootView.findViewById(R.id.tv_single_step_detail);
        Button button = rootView.findViewById(R.id.button_next_step);

        String description = null;
        String videoUrl = null;
        String thumbnailUrl = null;
        try {
            description = JSONUtils.getStepInfoFromKey(mRecipe, mStepNumber, "description");
            videoUrl = JSONUtils.getStepInfoFromKey(mRecipe, mStepNumber, "videoURL");
            thumbnailUrl = JSONUtils.getStepInfoFromKey(mRecipe, mStepNumber, "thumbnailURL");
        } catch (JSONException e) {
            e.printStackTrace();
        }



        Log.d(TAG, "onCreateView: " + videoUrl);
        Log.d(TAG, "onCreateView:  " + description);

        mPlayerView.setDefaultArtwork(BitmapFactory.decodeResource(getResources(),R.drawable.exo_controls_next));

        stepTextView.setText(description);

        setExoplayer(videoUrl,thumbnailUrl);

        if(getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE && mTwoPane == false ){
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) mPlayerView.getLayoutParams();
            params.width=params.MATCH_PARENT;
            params.height=params.MATCH_PARENT;
            mPlayerView.setLayoutParams(params);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mButtonCallBack.onButtonClick();

            }
        });

        if(mTwoPane){
            button.setVisibility(View.GONE);
        }


        return rootView;
    }

    private void setExoplayer(String videoUrl, String thumbnailUrl) {

        String stringUrl=(videoUrl == "" || videoUrl == null)?thumbnailUrl:videoUrl;

        if(stringUrl=="" || stringUrl ==null){
            return;
        }

        Uri uri = Uri.parse(stringUrl);

        if (mPlayer == null) {

            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mPlayerView.setPlayer(mPlayer);

            String agent = Util.getUserAgent(getContext(),"BakingTime");
            MediaSource mediaSource = new ExtractorMediaSource(uri, new DefaultDataSourceFactory(getContext(), agent)
                    , new DefaultExtractorsFactory(), null, null);
            mPlayer.prepare(mediaSource);
            mPlayer.setPlayWhenReady(true);

        }

    }

    void setStepNumber(int step){
        mStepNumber = step;
    }
    void setRecipeObject(JSONObject recipe){
        mRecipe = recipe;
    }
    void setTwoPane(){
        mTwoPane = true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("jsonString", mRecipe.toString());
        outState.putInt("stepNumber", mStepNumber);
        outState.putBoolean("isTwoPane", mTwoPane);
    }

    @Override
    public void onPause() {
        super.onPause();
        if(mPlayer!=null){
        mPlayer.stop();
        mPlayer.release();
    }
    }
}
