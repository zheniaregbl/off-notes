package com.nimain.core.domain.usecase

import com.nimain.core.domain.repository.NoteRepository

class SaveNoteUseCase(private val repository: NoteRepository) {
    suspend operator fun invoke(id: String, title: String, content: String) =
        repository.saveNote(id, title, content)
}