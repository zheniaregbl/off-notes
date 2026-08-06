package com.nimain.home.presentation

internal sealed interface HomeAction {
    data object OnCreateNote : HomeAction
    data object OnClickNote : HomeAction
    data object OnRefreshNotes : HomeAction
}