package com.nimain.home.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.nimain.core.presentation.theme.BackgroundColor
import com.nimain.home.presentation.HomeScreenContent
import com.nimain.home.presentation.HomeScreenState
import com.nimain.home.presentation.model.NoteUiModel

@Composable
@Preview
private fun PreviewHomeScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
    ) {
        HomeScreenContent(
            modifier = Modifier
                .fillMaxSize(),
            state = HomeScreenState.Success(
                notes = listOf(
                    NoteUiModel("1", "Title 1", "This is the text"),
                    NoteUiModel("2", "Title 2", "This is the text"),
                ),
                searchQuery = ""
            ),
            onNoteClick = {}
        )
    }
}