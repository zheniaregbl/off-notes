package com.nimain.home.presentation.mapper

import com.nimain.core.domain.model.Note
import com.nimain.core.presentation.util.hsl.NoteColorGenerator
import com.nimain.core.presentation.util.hsl.toComposeColor
import com.nimain.home.presentation.model.NoteUiModel

internal fun Note.toUiModel() =
    NoteUiModel(
        id = id,
        title = title,
        preview = content,
        backgroundColor = NoteColorGenerator
            .generate(title)
            .toComposeColor()
    )