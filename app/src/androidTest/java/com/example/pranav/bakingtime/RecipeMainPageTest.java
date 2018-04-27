package com.example.pranav.bakingtime;

import android.os.SystemClock;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.ViewAssertion;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;

@RunWith(AndroidJUnit4.class)
public class RecipeMainPageTest {

    @Rule
    public ActivityTestRule<Recipe> mRecipeTestRule =  new ActivityTestRule<>(Recipe.class);


    @Test
    public void checkDisplayed_RecipeRecyclerView(){

        onView(withId(R.id.recipe_recyclerview)).check(matches(isDisplayed()));
    }


    @Test
    public void clickRecipe_OpenRecipeDetail() {

        onView(withId(R.id.recipe_recyclerview))
                .perform(actionOnItemAtPosition(0, click()));

        onView(withId(R.id.tv_recipe_name)).check(matches(withText("Nutella Pie")));


    }
}
