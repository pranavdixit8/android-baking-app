package com.example.pranav.bakingtime;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.action.RemoteViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.v4.app.Fragment;

import com.example.pranav.bakingtime.TestData.DummyData;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;

@RunWith(JUnit4.class)
public class RecipeDetailTest {



    @Rule
    public ActivityTestRule<RecipeDetail> mActivityTestRule = new ActivityTestRule<>(RecipeDetail.class);


    @Before
    public void setup(){
        RecipeDetailFragment fragment = new RecipeDetailFragment();
        fragment.setRecipeObject(DummyData.getdata());

        mActivityTestRule.getActivity().getSupportFragmentManager().beginTransaction().add(R.id.steps_container,fragment).commit();
    }

    @Test
    public void checkDisplayed_FragmentContainer(){

        onView(withId(R.id.steps_container)).check(matches(isDisplayed()));
    }

    @Test
    public void checkDisplayed_FragmentRecyclerView(){

        onView(withId(R.id.recipe_step_list_recyclerview)).check(matches(isDisplayed()));

    }

    @Test
    public void checkDisplayed_RecipeName(){

        onView(withId(R.id.tv_recipe_name)).check(matches(isDisplayed()));
    }


}
