package com.nimain.core.domain.usecase

import com.nimain.core.domain.repository.NoteRepository

class CreateNoteUseCase(private val repository: NoteRepository) {
    suspend operator fun invoke() = repository.createNote()
}
