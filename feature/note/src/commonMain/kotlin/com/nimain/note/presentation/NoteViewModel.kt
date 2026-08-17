package com.nimain.note.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nimain.core.domain.usecase.CreateNoteUseCase
import com.nimain.core.domain.usecase.GetNoteUseCase
import com.nimain.core.domain.usecase.SaveNoteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class NoteViewModel(
    private val noteId: String?,
    private val getNoteUseCase: GetNoteUseCase,
    private val saveNoteUseCase: SaveNoteUseCase,
    private val createNoteUseCase: CreateNoteUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(NoteState())
    val state: StateFlow<NoteScreenState> = _state
        .map { it.toUiState() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = NoteState().toUiState()
        )

    init { initNoteScreenData(noteId) }

    fun onAction(action: NoteAction) {
        when (action) {
            NoteAction.OnConfirm -> saveNote()
            is NoteAction.OnContentChange -> _state.update { it.copy(content = action.value) }
            is NoteAction.OnTitleChange -> _state.update { it.copy(title = action.value) }
        }
    }

    private fun initNoteScreenData(noteId: String?) {
        viewModelScope.launch {
            if (noteId != null) getNote(noteId)
            else {
                val note = createNoteUseCase()
                _state.update {
                    it.copy(
                        id = note.id,
                        title = note.title,
                        content = "",
                        lastModifier = note.lastModified
                    )
                }
            }
        }
    }

    private fun getNote(id: String) {
        viewModelScope.launch {
            runCatching { getNoteUseCase(id) }
                .onSuccess { note ->
                    if (note != null)
                        _state.update {
                            it.copy(
                                id = note.id,
                                title = note.title,
                                content = note.content,
                                lastModifier = note.lastModified
                            )
                        }
                }
                .onFailure {
                    _state.update {
                        it.copy(
                            id = "",
                            title = "",
                            content = "Error",
                            lastModifier = ""
                        )
                    }
                }
        }
    }

    private fun saveNote() {
        viewModelScope.launch {
            saveNoteUseCase(_state.value.id, _state.value.title, _state.value.content)
        }
    }
}