package com.nimain.note.presentation

internal sealed interface NoteEvent {
    data object Saved : NoteEvent
    data class SaveFailed(val cause: Throwable) : NoteEvent
}