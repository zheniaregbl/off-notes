package com.nimain.core.domain.usecase

import com.nimain.core.domain.repository.NoteRepository

class CreateNoteUseCase(private val repository: NoteRepository) {
    suspend operator fun invoke(title: String, content: String) = repository.createNote(title, content)
}
