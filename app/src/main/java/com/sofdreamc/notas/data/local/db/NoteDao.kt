package com.sofdreamc.notas.data.local.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sofdreamc.notas.data.local.models.Note

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(note: Note): Long

    @Query("SELECT * FROM notes ORDER BY important DESC")
    fun getAllNotes(): LiveData<List<Note>>

    @Query("SELECT* FROM notes WHERE id = :id")
    fun find(id: Int): LiveData<Note>

    @Delete
    suspend fun deleteNote(note: Note)
}
