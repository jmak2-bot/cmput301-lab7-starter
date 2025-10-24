package com.example.androiduitesting;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {
    @BeforeClass
    public static void disableAnimations() {
        androidx.test.platform.app.InstrumentationRegistry.getInstrumentation().getUiAutomation()
                .executeShellCommand("settings put global window_animation_scale 0");
        androidx.test.platform.app.InstrumentationRegistry.getInstrumentation().getUiAutomation()
                .executeShellCommand("settings put global transition_animation_scale 0");
        androidx.test.platform.app.InstrumentationRegistry.getInstrumentation().getUiAutomation()
                .executeShellCommand("settings put global animator_duration_scale 0");
    }

    @Rule
    public ActivityScenarioRule<MainActivity> scenario = new
            ActivityScenarioRule<MainActivity>(MainActivity.class);

    @Test
    public void testAddCity(){
// Click on Add City button
        onView(withId(R.id.button_add)).perform(click());
// Type "Edmonton" in the editText
        onView(withId(R.id.editText_name)).perform(ViewActions.typeText("Edmonton"));
// Click on Confirm
                onView(withId(R.id.button_confirm)).perform(click());
// Check if text "Edmonton" is matched with any of the text displayed on the screen
        onView(withText("Edmonton")).check(matches(isDisplayed()));
    }
    @Test
    public void testClearCity(){
// Add first city to the list
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(ViewActions.typeText("Edmonton"));
                onView(withId(R.id.button_confirm)).perform(click());
//Add another city to the list
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(ViewActions.typeText("Vancouver"));
                onView(withId(R.id.button_confirm)).perform(click());
//Clear the list
        onView(withId(R.id.button_clear)).perform(click());
        onView(withText("Edmonton")).check(doesNotExist());
        onView(withText("Vancouver")).check(doesNotExist());
    }
    @Test
    public void testListView(){
// Add a city
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(ViewActions.typeText("Edmonton"));
                onView(withId(R.id.button_confirm)).perform(click());
// Check if in the Adapter view (given id of that adapter view),there is a data
// (which is an instance of String) located at position zero.
// If this data matches the text we provided then Voila! Our test should pass
// You can also use anything() in place of is(instanceOf(String.class))
        onData(is(instanceOf(String.class))).inAdapterView(withId(R.id.city_list
        )).atPosition(0).check(matches((withText("Edmonton"))));
    }


    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);


    //  Check if activity switched correctly
    @Test
    public void testActivitySwitch() {
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(ViewActions.typeText("Edmonton"));
        onView(withId(R.id.button_confirm)).perform(click());

        onView(withText("Edmonton")).perform(click());  // checks if display changed
        onView(withId(R.id.text_city_name)).check(matches(isDisplayed()));
    }

    // Check if city name is consistent
    @Test
    public void testCityNameDisplayed() {
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(ViewActions.typeText("Edmonton")); //adds city
        onView(withId(R.id.button_confirm)).perform(click());

        onView(withText("Edmonton")).perform(click());
        onView(withId(R.id.text_city_name)).check(matches(withText("Edmonton"))); // checks if city name is there
    }

    //  Test back button returns to main
    @Test
    public void testBackButton() throws InterruptedException {
        // Add a city
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name))
                .perform(ViewActions.typeText("Calgary"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.button_confirm)).perform(click());

        // Click to open ShowActivity
        onView(withText("Calgary")).perform(click());

        // Wait for ShowActivity to appear cuz for some reason it clicks to fast
        Thread.sleep(500);

        // Click the back button
        onView(withId(R.id.button_back)).perform(click());

        // Wait for MainActivity to reappear
        Thread.sleep(500);

        // Check that MainActivity UI is visible again
        onView(withId(R.id.button_add)).check(matches(isDisplayed()));
    }}



