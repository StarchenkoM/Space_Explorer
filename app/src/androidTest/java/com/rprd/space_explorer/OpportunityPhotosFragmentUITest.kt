package com.rprd.space_explorer

import android.view.Gravity
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.DrawerMatchers
import androidx.test.espresso.contrib.NavigationViewActions.navigateTo
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.space_explorer.presentation.activities.MainActivity
import com.example.space_explorer.presentation.adapters.CuriosityAdapter
import com.example.space_explorer.presentation.fragments.CuriosityFragment
import com.rprd.space_explorer.presentation.activities.MainActivity
import com.rprd.space_explorer.presentation.adapters.CuriosityAdapter
import com.rprd.space_explorer.presentation.adapters.RoverPhotosViewHolder
import com.rprd.space_explorer.presentation.fragments.CuriosityFragment
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
@HiltAndroidTest
class OpportunityPhotosFragmentUITest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private lateinit var activityScenario: ActivityScenario<MainActivity>
    private lateinit var roverName: String
    private val itemPositionForScroll: Int = 5

    @Before
    fun setup() {
        hiltRule.inject()
        activityScenario = ActivityScenario.launch(MainActivity::class.java)
        roverName = "Opportunity photos 95"
    }

    @After
    fun reset() {
        activityScenario.close()
    }


    @ExperimentalCoroutinesApi
    @Test
    fun isDrawerClosed() {
        Espresso.onView(withId(R.id.drawer_layout)).check(matches(DrawerMatchers.isClosed(Gravity.START)))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun isDrawerOpen() {
        Espresso.onView(withId(R.id.drawer_layout))
                .check(matches(DrawerMatchers.isClosed(Gravity.START)))
                .perform(DrawerActions.open())
                .check(matches(DrawerMatchers.isOpen(Gravity.START)))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun navigateToCuriosityPhotosFragment() {
        Espresso.onView(withId(R.id.drawer_layout)).perform(DrawerActions.open())
        Espresso.onView(withId(R.id.nav_view)).perform(navigateTo(R.id.opportunityRoverPhotoFragment))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun isRoverIconDisplayed() {
        launchFragmentInHiltContainer<CuriosityFragment> { }
        Espresso.onView(withId(R.id.rover_icon)).check(matches(isDisplayed()))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun isRoverNameDisplayed() {
        Espresso.onView(withId(R.id.drawer_layout)).perform(DrawerActions.open())
        Espresso.onView(withId(R.id.nav_view)).perform(navigateTo(R.id.opportunityRoverPhotoFragment))
        Espresso.onView(withId(R.id.rover_name)).check(matches(isDisplayed()))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun isRoverNameCorrect() {
        Espresso.onView(withId(R.id.drawer_layout)).perform(DrawerActions.open())
        Espresso.onView(withId(R.id.nav_view)).perform(navigateTo(R.id.opportunityRoverPhotoFragment))
        Espresso.onView(withId(R.id.rover_name)).check(matches(withText(roverName)))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun isRoverPhotosRecyclerDisplayed() {
        launchFragmentInHiltContainer<CuriosityFragment> { }
        Espresso.onView(withId(R.id.opportunity_recycler)).check(matches(isDisplayed()))
    }


    @ExperimentalCoroutinesApi
    @Test
    fun isPreviousButtonDisplayed() {
        launchFragmentInHiltContainer<CuriosityFragment> { }
        Espresso.onView(withId(R.id.previous_arrow)).check(matches(isDisplayed()))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun isNextButtonDisplayed() {
        launchFragmentInHiltContainer<CuriosityFragment> { }
        Espresso.onView(withId(R.id.next_arrow)).check(matches(isDisplayed()))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun scrollToRecyclerItem() {
        Espresso.onView(withId(R.id.drawer_layout)).perform(DrawerActions.open())
        Espresso.onView(withId(R.id.nav_view)).perform(navigateTo(R.id.opportunityRoverPhotoFragment))
        Espresso.onView(withId(R.id.opportunity_recycler)).perform(
                RecyclerViewActions.scrollToPosition<RoverPhotosViewHolder>(
                        itemPositionForScroll
                )
        )
    }

}