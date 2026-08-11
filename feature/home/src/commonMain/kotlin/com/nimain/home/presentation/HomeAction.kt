package com.nimain.home.presentation

internal sealed interface HomeAction {
    data class OnSearchChange(val query: String) : HomeAction
    data class OnDeleteNote(val id: String) : HomeAction
    data object OnRefreshNotes : HomeAction
}