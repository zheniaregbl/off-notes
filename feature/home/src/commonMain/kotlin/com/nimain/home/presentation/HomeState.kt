package com.nimain.home.presentation

import com.nimain.home.presentation.model.NoteUiModel

internal data class HomeState(
    val hasLoadedOnce: Boolean = false,
    val isRefreshing: Boolean = false,
    val allNotes: List<NoteUiModel> = emptyList(),
    val searchQuery: String = "",
    val errorMessage: String? = null
) {
    private val filteredNotes: List<NoteUiModel>
        get() = if (searchQuery.isBlank()) {
            allNotes
        } else {
            allNotes.filter { note ->
                note.title.contains(searchQuery, ignoreCase = true) ||
                        note.preview.contains(searchQuery, ignoreCase = true)
            }
        }

    fun toUiState(): HomeScreenState = when {
        !hasLoadedOnce && errorMessage != null -> HomeScreenState.Error(errorMessage)
        !hasLoadedOnce -> HomeScreenState.Loading
        allNotes.isEmpty() -> HomeScreenState.Empty
        else -> HomeScreenState.Success(
            notes = filteredNotes,
            searchQuery = searchQuery,
            isRefreshing = isRefreshing,
            errorMessage = errorMessage
        )
    }
}