package com.nimain.home.presentation.mapper

import com.nimain.core.domain.model.Note
import com.nimain.home.presentation.model.NoteUiModel

internal fun Note.toUiModel() =
    NoteUiModel(
        id = id,
        title = title,
        preview = content
    )