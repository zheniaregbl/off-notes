package com.nimain.core.domain.usecase

import com.nimain.core.domain.model.Note
import com.nimain.core.domain.repository.NoteRepository

class CreateNoteUseCase(private val repository: NoteRepository) {
    suspend operator fun invoke(): Note = repository.createNote()
}
