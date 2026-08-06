package com.nimain.core.domain.repository

import com.nimain.core.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun observeNotes(): Flow<List<Note>>
    suspend fun createNote(): Note
    suspend fun getNote(id: String): Note?
    suspend fun saveNote(id: String, content: String)
    suspend fun deleteNote(id: String)
    suspend fun refresh()
}