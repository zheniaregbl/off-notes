package com.nimain.home.presentation.mapper

import com.nimain.core.domain.model.Note
import com.nimain.home.presentation.model.NoteUiModel

internal fun Note.toUiModel(): NoteUiModel {
    val bodyPreview = content
        .lineSequence()
        .drop(if (content.startsWith("# ")) 1 else 0)
        .joinToString("\n")
        .trim()

    return NoteUiModel(
        id = id,
        title = title,
        preview = bodyPreview
    )
}
