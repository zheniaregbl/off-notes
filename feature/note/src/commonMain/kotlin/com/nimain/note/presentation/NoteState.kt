package com.nimain.note.presentation

internal data class NoteState(
    val id: String = "",
    val currentTitle: String = "",
    val originTitle: String = "",
    val content: String = "",
    val lastModifier: String = ""
) {
    fun toUiState(): NoteScreenState = when {
        else -> NoteScreenState.Success(id, currentTitle, originTitle, content, lastModifier)
    }
}