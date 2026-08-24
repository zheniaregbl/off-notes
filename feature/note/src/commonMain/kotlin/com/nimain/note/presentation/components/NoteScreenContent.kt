package com.nimain.note.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import com.nimain.note.presentation.NoteAction
import com.nimain.note.presentation.NoteScreenState

@Composable
internal expect fun NoteScreenContent(
    modifier: Modifier = Modifier,
    state: State<NoteScreenState>,
    onAction: (NoteAction) -> Unit,
    onBack: () -> Unit
)