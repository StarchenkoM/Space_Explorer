package com.rprd.space_explorer

import android.view.Gravity
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.DrawerActions.open
import androidx.test.espresso.contrib.DrawerMatchers.isClosed
import androidx.test.espresso.contrib.DrawerMatchers.isOpen
import androidx.test.espresso.contrib.NavigationViewActions.navigateTo
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.rprd.space_explorer.presentation.activities.MainActivity
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
class DailyPhotoFragmentUITest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private lateinit var activityScenario: ActivityScenario<MainActivity>

    @Before
    fun setup() {
        hiltRule.inject()

        activityScenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @After
    fun reset() {
        activityScenario.close()
    }


    @ExperimentalCoroutinesApi
    @Test
    fun isDrawerClosed() {
        onView(withId(R.id.drawer_layout)).check(matches(isClosed(Gravity.START))) // Left Drawer should be closed.
    }

    @ExperimentalCoroutinesApi
    @Test
    fun isDrawerOpen() {
        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.START))) // Left Drawer should be closed.
                .perform(open()) // Open Drawer
                .check(matches(isOpen(Gravity.START)))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun navigateToDailyPhotoFragment() {
        onView(withId(R.id.drawer_layout)).perform(open()) // Open Drawer
        onView(withId(R.id.nav_view)).perform(navigateTo(R.id.dailyPhotoFragment))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun isDailyPhotoPictureDisplayed() {
        navigateTo(R.id.dailyPhotoFragment)
        onView(withId(R.id.daily_photo_iv)).check(matches(isDisplayed()))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun isDailyPhotoTitleDisplayed() {
        navigateTo(R.id.dailyPhotoFragment)
        onView(withId(R.id.daily_photo_title_tv)).check(matches(isDisplayed()))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun isDailyPhotoTimeDisplayed() {
        navigateTo(R.id.dailyPhotoFragment)
        onView(withId(R.id.daily_photo_date_tv)).check(matches(isDisplayed()))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun isDailyPhotoExplanationDisplayed() {
        navigateTo(R.id.dailyPhotoFragment)
        onView(withId(R.id.daily_photo_explanation_tv)).check(matches(isDisplayed()))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun isDailyPhotoPreviousButtonDisplayed() {
        navigateTo(R.id.dailyPhotoFragment)
        onView(withId(R.id.previous_day)).check(matches(isDisplayed()))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun isDailyPhotoNextButtonDisplayed() {
        navigateTo(R.id.dailyPhotoFragment)
        onView(withId(R.id.next_day)).check(matches(isDisplayed()))
    }


}