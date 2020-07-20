package com.sofdreamc.notas.data.repository

import com.sofdreamc.notas.data.local.db.NoteDao
import com.sofdreamc.notas.data.local.models.Note
import javax.inject.Inject

class NoteRepository @Inject constructor(
    val noteDao: NoteDao
) {
    fun find(id: Int) = noteDao.find(id)

    fun getAllNotes() = noteDao.getAllNotes()

    suspend fun upsertNote(note: Note) = noteDao.upsert(note)

    suspend fun deleteNote(note: Note) = noteDao.deleteNote(note)
}