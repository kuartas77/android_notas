package com.sofdreamc.notas.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sofdreamc.notas.data.local.models.Note

@Database(
    entities = [Note::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun getNoteDao(): NoteDao
}