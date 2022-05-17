package org.booleanull.pushalerts


import androidx.test.espresso.DataInteraction
import androidx.test.espresso.ViewInteraction
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent

import androidx.test.InstrumentationRegistry.getInstrumentation
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*

import org.booleanull.pushalerts.R

import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.anything
import org.hamcrest.Matchers.`is`

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    object AndroidTestMockFactory {
        const val FILTER = "filter"
    }

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun checkSuccessAddNewFilter() {
        // Секция поиска элементов на экране
        val button = onView(withId(R.id.dialog_button))
        val editText = onView(withId(R.id.filterEditText))
        val nextFragment = onView(withId(R.id.fragment_container_view_tag))

        // Секция взаимодействия с компонентами
        editText.perform(typeText(AndroidTestMockFactory.FILTER))
        button.perform(click())

        // Секция проверки
        nextFragment.check(matches(isDisplayed()))
    }
}
