package com.nimain.home.presentation.mapper

import com.nimain.home.domain.model.Note
import com.nimain.home.presentation.model.NoteUiModel

internal fun Note.toUiModel() = NoteUiModel(id, title, content)