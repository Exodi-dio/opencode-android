package com.opencode.android

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Rule
import org.junit.Test

// M1 launch smoke: MainActivity launches and its root view is displayed.
class LaunchTest {
  @get:Rule
  val rule = ActivityScenarioRule(MainActivity::class.java)

  @Test
  fun launch_displaysRoot() {
    onView(isRoot()).check(matches(isDisplayed()))
  }
}
