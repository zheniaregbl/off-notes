package com.nimain.core.domain.usecase

import com.nimain.core.domain.repository.NoteRepository

class RefreshNotesUseCase(private val repository: NoteRepository) {
    suspend operator fun invoke() = repository.refresh()
}
