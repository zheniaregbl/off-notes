package com.nimain.home.presentation.model

import androidx.compose.ui.graphics.Color

data class NoteUiModel(
    val id: String,
    val title: String,
    val preview: String,
    val backgroundColor: Color
)
