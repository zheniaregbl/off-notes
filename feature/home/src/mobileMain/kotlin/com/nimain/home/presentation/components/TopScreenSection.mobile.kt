package com.nimain.home.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier

@Composable
internal actual fun TopScreenSection(
    modifier: Modifier,
    searchQuery: State<String>,
    onSearchQueryChange: (String) -> Unit,
    onAddNote: () -> Unit
) {
    SearchBar(
        modifier = modifier,
        value = searchQuery.value,
        hint = "Input text...",
        onValueChange = onSearchQueryChange
    )
}