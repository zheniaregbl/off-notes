package com.nimain.home.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nimain.core.presentation.components.DialogPopup
import com.nimain.core.presentation.theme.BackgroundColor
import com.nimain.home.presentation.HomeAction

@Composable
internal fun NoteActionDialog(
    showDialog: Boolean,
    selectedNoteId: String,
    onAction: (HomeAction) -> Unit,
    onDismissRequest: () -> Unit
) {
    DialogPopup(
        showDialog = showDialog,
        onDismissRequest = onDismissRequest
    ) {
        NoteActionDialogContent(
            modifier = Modifier
                .fillMaxWidth()
                .background(BackgroundColor),
            selectedNoteId = selectedNoteId,
            onAction = onAction,
            onDismissRequest = onDismissRequest
        )
    }
}

@Composable
private fun NoteActionDialogContent(
    modifier: Modifier = Modifier,
    selectedNoteId: String,
    onAction: (HomeAction) -> Unit,
    onDismissRequest: () -> Unit
) {
    Column(modifier = modifier) {
        NoteActionRow(
            modifier = Modifier.fillMaxWidth(),
            text = "Delete",
            contentColor = Color.Red,
            onClick = {
                onAction(HomeAction.OnDeleteNote(selectedNoteId))
                onDismissRequest()
            }
        )
        NoteActionRow(
            modifier = Modifier.fillMaxWidth(),
            text = "Rename",
            contentColor = Color.White,
            onClick = onDismissRequest
        )
        NoteActionRow(
            modifier = Modifier.fillMaxWidth(),
            text = "Duplicate",
            contentColor = Color.White,
            onClick = onDismissRequest
        )
    }
}

@Composable
private fun NoteActionRow(
    modifier: Modifier = Modifier,
    text: String,
    contentColor: Color,
    onClick: () -> Unit = { }
) {
    Row(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 10.dp)
    ) {
        Text(
            text = text,
            style = TextStyle(
                color = contentColor,
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal
            )
        )
    }
}

@Preview
@Composable
private fun NoteActionDialogPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
    ) {
        NoteActionDialog(
            showDialog = true,
            selectedNoteId = "1",
            onAction = { },
            onDismissRequest = { }
        )
    }
}