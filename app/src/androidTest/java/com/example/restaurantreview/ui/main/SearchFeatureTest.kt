package com.example.restaurantreview.ui.main

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.restaurantreview.R
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class SearchFeatureTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun searchingUser() {
        onView(withId(R.id.search_bar)).check(matches(isDisplayed())).perform(click())

        val searchTextInput =
            onView(withId(com.google.android.material.R.id.open_search_view_edit_text))
        searchTextInput.perform(replaceText("android"), closeSoftKeyboard())
        searchTextInput.perform(pressImeActionButton())

        Thread.sleep(3000)

        val searchResultText = onView(
            allOf( withId(R.id.tv_item), withText("android"), withParent(
                allOf(
                    withId(R.id.item_review),
                    withParent(withId(R.id.rv_review))
                )
            ), isDisplayed())
        )
        searchResultText.check(matches(withText("android")))
    }
}
