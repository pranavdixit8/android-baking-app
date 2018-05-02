package com.example.pranav.bakingtime;


import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;
import static android.content.res.Configuration.ORIENTATION_PORTRAIT;
import static android.view.View.GONE;

public class RecipeStepFragment extends android.support.v4.app.Fragment {

    private static final String TAG = RecipeStepFragment.class.getSimpleName();
    private static final String IS_TWO_PANE = "isTwoPane";
    private static final String DESCRIPTION_KEY = "description";
    private static final String VIDEO_URL_KEY = "videoURL";
    private static final String THUMBNAIL_URL_KEY = "thumbnailURL";
    private static final String APPLICATION_NAME = "BakingTime";
    public static final String PLAYER_POSITION_KEY = "playerPosition";

    int mStepNumber;
    private JSONObject mRecipe;
    private boolean mTwoPane;
    private SimpleExoPlayer mPlayer;
    private SimpleExoPlayerView mPlayerView;
    private ImageView mVideoThumbnail;
    private long mPlayerPosition;

    private FrameLayout mFrameLayout;

    OnButtonClickListener mButtonCallBack;

    interface OnButtonClickListener {
        void onButtonClick();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_step_detail, container, false);

        if (savedInstanceState != null) {
            String jsonString = savedInstanceState.getString(Recipe.JSON_STRING);
            mStepNumber = savedInstanceState.getInt(RecipeDetail.STEP_NUMBER);
            try {
                mRecipe = new JSONObject(jsonString);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mTwoPane = savedInstanceState.getBoolean(IS_TWO_PANE);
            mPlayerPosition = savedInstanceState.getLong(PLAYER_POSITION_KEY);

        }

        mPlayerView = rootView.findViewById(R.id.sepv_media_player);
        mVideoThumbnail = rootView.findViewById(R.id.iv_video_thimbnail);
        mFrameLayout = rootView.findViewById(R.id.video_image_frame);
        TextView stepTextView = rootView.findViewById(R.id.tv_single_step_detail);
        Button button = rootView.findViewById(R.id.button_next_step);
        CardView stepCardView = rootView.findViewById(R.id.cv_recipe);

        String description = null;
        String videoUrl = null;
        String thumbnailUrl = null;
        try {
            description = JSONUtils.getStepInfoFromKey(mRecipe, mStepNumber, DESCRIPTION_KEY);
            videoUrl = JSONUtils.getStepInfoFromKey(mRecipe, mStepNumber, VIDEO_URL_KEY);
            thumbnailUrl = JSONUtils.getStepInfoFromKey(mRecipe, mStepNumber, THUMBNAIL_URL_KEY);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mPlayerView.setDefaultArtwork(BitmapFactory.decodeResource(getResources(), R.drawable.exo_controls_next));

        stepTextView.setText(description);

        if (videoUrl == null || videoUrl.isEmpty()) {
            setVideoThumbnail(thumbnailUrl);
        } else {

            setExoplayer(videoUrl);
        }


        if (getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE && mTwoPane == false) {
            stepCardView.setVisibility(GONE);
            button.setVisibility(GONE);

            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) mFrameLayout.getLayoutParams();
            params.width = params.MATCH_PARENT;
            params.height = params.MATCH_PARENT;

            mFrameLayout.setLayoutParams(params);
        } else if (getResources().getConfiguration().orientation == ORIENTATION_PORTRAIT && mTwoPane == false) {
            stepCardView.setVisibility(View.VISIBLE);
            button.setVisibility(View.VISIBLE);

        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mButtonCallBack.onButtonClick();

            }
        });

        if (mTwoPane) {
            button.setVisibility(GONE);
        }


        return rootView;
    }

    private void setVideoThumbnail(String thumbnailUrl) {
        if (thumbnailUrl == null || thumbnailUrl.isEmpty()) {
            return;
        }
        Picasso.with(mVideoThumbnail.getContext()).load(thumbnailUrl).into(mVideoThumbnail);
    }

    private void setExoplayer(String videoUrl) {


        Uri uri = Uri.parse(videoUrl);

        if (mPlayer == null) {

            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mPlayerView.setPlayer(mPlayer);

            String agent = Util.getUserAgent(getContext(), APPLICATION_NAME);
            MediaSource mediaSource = new ExtractorMediaSource(uri, new DefaultDataSourceFactory(getContext(), agent)
                    , new DefaultExtractorsFactory(), null, null);

            mPlayer.seekTo(mPlayerPosition);
            mPlayer.prepare(mediaSource);
            mPlayer.setPlayWhenReady(true);

        }

    }

    void setStepNumber(int step) {
        mStepNumber = step;
    }

    void setRecipeObject(JSONObject recipe) {
        mRecipe = recipe;
    }

    void setTwoPane(boolean isTwoPane) {
        mTwoPane = isTwoPane;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(Recipe.JSON_STRING, mRecipe.toString());
        outState.putInt(RecipeDetail.STEP_NUMBER, mStepNumber);
        outState.putBoolean(IS_TWO_PANE, mTwoPane);
        outState.putLong(PLAYER_POSITION_KEY, mPlayerPosition);
    }

    @Override
    public void onPause() {
        super.onPause();
        mPlayerPosition = mPlayer.getCurrentPosition();
        if(mPlayer!=null){
        mPlayer.stop();
        mPlayer.release();
        mPlayer = null;
    }
    }

}
