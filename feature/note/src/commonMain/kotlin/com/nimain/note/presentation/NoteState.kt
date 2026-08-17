package com.nimain.note.presentation

internal data class NoteState(
    val id: String = "",
    val title: String = "",
    val content: String = "",
    val lastModifier: String = ""
) {
    fun toUiState(): NoteScreenState = when {
        else -> NoteScreenState.Success(id, title, content, lastModifier)
    }
}