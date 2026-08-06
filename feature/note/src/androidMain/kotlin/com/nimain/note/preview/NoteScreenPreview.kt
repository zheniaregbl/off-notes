package com.nimain.note.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.nimain.core.presentation.theme.BackgroundColor
import com.nimain.note.presentation.NoteScreenContent

@Composable
@Preview
private fun NoteScreenPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
    ) {
        NoteScreenContent(
            modifier = Modifier.fillMaxSize(),
            onBack = {}
        )
    }
}