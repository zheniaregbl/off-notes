package com.nimain.home.preview.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nimain.core.presentation.theme.BackgroundColor
import com.nimain.home.presentation.components.NoteItem
import com.nimain.home.presentation.model.NoteUiModel

@Composable
@Preview
private fun NotePreview() {
    Box(
        modifier = Modifier
            .background(BackgroundColor)
            .padding(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            NoteItem(
                modifier = Modifier.weight(1f),
                noteUiModel = NoteUiModel("", "Preview note", "this note contains some words for show you how looks content in this note card."),
                onClick = { }
            )
            Spacer(Modifier.width(10.dp))
            NoteItem(
                modifier = Modifier.weight(1f),
                noteUiModel = NoteUiModel("", "Preview note", "this note contains some words for show you how looks content in this note card."),
                onClick = { }
            )
        }
    }
}