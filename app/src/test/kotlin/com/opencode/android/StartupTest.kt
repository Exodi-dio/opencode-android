package com.opencode.android

import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class StartupTest {
  @Test fun activityLaunches() {
    val ctrl = Robolectric.buildActivity(MainActivity::class.java).setup()
    assertNotNull(ctrl.get())
  }
}
