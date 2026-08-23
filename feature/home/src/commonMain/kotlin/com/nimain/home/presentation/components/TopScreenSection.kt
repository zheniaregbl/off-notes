package com.nimain.home.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier

@Composable
internal expect fun TopScreenSection(
    modifier: Modifier,
    searchQuery: State<String>,
    onSearchQueryChange: (String) -> Unit,
    onAddNote: () -> Unit
)