package com.opencode.session

import android.content.Context
import androidx.room.Database
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Room
import androidx.room.RoomDatabase

@Entity(tableName = "sessions")
data class SessionEntity(
  @PrimaryKey val id: String,
  val createdAt: Long,
)

@Entity(
  tableName = "message_parts",
  foreignKeys = [
    ForeignKey(
      entity = SessionEntity::class,
      parentColumns = ["id"],
      childColumns = ["sessionId"],
      onDelete = ForeignKey.CASCADE,
    ),
  ],
  indices = [Index("sessionId")],
)
data class MessagePartEntity(
  @PrimaryKey val partId: String,
  val sessionId: String,
  val kind: String,
  val payloadJson: String,
)

@Database(
  entities = [SessionEntity::class, MessagePartEntity::class],
  version = 1,
  exportSchema = true,
)
abstract class SessionDatabase : RoomDatabase() {
  abstract fun sessionDao(): SessionDao

  companion object {
    fun database(context: Context): SessionDatabase =
      Room.databaseBuilder(context, SessionDatabase::class.java, "opencode.db")
        .fallbackToDestructiveMigration()
        .build()
  }
}
