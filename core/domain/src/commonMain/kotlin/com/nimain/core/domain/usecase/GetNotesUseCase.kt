package com.nimain.core.domain.usecase

import com.nimain.core.domain.model.Note
import com.nimain.core.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class GetNotesUseCase(private val repository: NoteRepository) {
    operator fun invoke(): Flow<List<Note>> = repository.observeNotes()
}