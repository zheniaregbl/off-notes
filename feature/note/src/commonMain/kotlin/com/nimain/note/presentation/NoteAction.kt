package com.nimain.note.presentation

internal sealed interface NoteAction {
    data object OnConfirm : NoteAction
    data class OnTitleChange(val value: String) : NoteAction
    data class OnContentChange(val value: String) : NoteAction
}