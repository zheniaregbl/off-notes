package com.nimain.core.domain.usecase

import com.nimain.core.domain.model.Note
import com.nimain.core.domain.repository.NoteRepository

class GetNoteUseCase(private val repository: NoteRepository) {
    suspend operator fun invoke(id: String): Note? = repository.getNote(id)
}
