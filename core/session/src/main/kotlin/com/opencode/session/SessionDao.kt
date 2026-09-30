package com.opencode.session

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SessionDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertSession(e: SessionEntity): Long

  @Query("SELECT * FROM message_parts WHERE sessionId = :sessionId")
  fun partsForSession(sessionId: String): List<MessagePartEntity>
}
