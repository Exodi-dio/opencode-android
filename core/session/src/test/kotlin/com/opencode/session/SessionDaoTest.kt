package com.opencode.session

import androidx.room.Room
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class SessionDaoTest {
  @Test fun sessionDaoRoundTrip() {
    val ctx = RuntimeEnvironment.getApplication()
    val db = Room.inMemoryDatabaseBuilder(ctx, SessionDatabase::class.java).allowMainThreadQueries().build()
    val id = db.sessionDao().insertSession(SessionEntity("s1", 1L))
    assertTrue(id >= 0)
    assertEquals(0, db.sessionDao().partsForSession("s1").size)
    db.close()
  }
}
