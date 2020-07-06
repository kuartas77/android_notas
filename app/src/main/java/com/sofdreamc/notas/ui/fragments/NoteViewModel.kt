package com.sofdreamc.notas.ui.fragments

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sofdreamc.notas.data.local.models.Note
import com.sofdreamc.notas.data.repository.NoteRepository
import kotlinx.coroutines.launch

class NoteViewModel @ViewModelInject constructor(
    private val noteRepository: NoteRepository
) : ViewModel() {

    fun getAllNotes() = noteRepository.getAllNotes()

    fun findNote(id: Int) = noteRepository.find(id)

    fun upsertNote(note: Note) = viewModelScope.launch {
        noteRepository.upsertNote(note)
    }

    fun deleteNote(note: Note) = viewModelScope.launch {
        noteRepository.deleteNote(note)
    }
}