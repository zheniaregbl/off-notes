package com.nimain.note.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nimain.core.domain.usecase.CreateNoteUseCase
import com.nimain.core.domain.usecase.GetNoteUseCase
import com.nimain.core.domain.usecase.SaveNoteUseCase
import com.nimain.core.domain.util.AppScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class NoteViewModel(
    private val noteId: String?,
    private val getNoteUseCase: GetNoteUseCase,
    private val saveNoteUseCase: SaveNoteUseCase,
    private val createNoteUseCase: CreateNoteUseCase,
    private val appScope: AppScope
) : ViewModel() {

    private companion object {
        const val AUTOSAVE_DEBOUNCE = 700L
    }

    private data class SaveSnapshot(
        val id: String,
        val title: String,
        val content: String
    )

    private val _state = MutableStateFlow(NoteState())
    val state: StateFlow<NoteScreenState> = _state
        .map { it.toUiState() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = NoteState().toUiState()
        )

    private val _events = Channel<NoteEvent>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    private val _saveRequests = Channel<Unit>(Channel.CONFLATED)
    private var _lastSaved: SaveSnapshot? = null

    init {
        observeAutoSave()
        initNoteScreenData(noteId)
    }

    fun onAction(action: NoteAction) {
        when (action) {
            NoteAction.OnConfirm -> confirmSave()
            NoteAction.OnTitleFocusLost -> _saveRequests.trySend(Unit)
            is NoteAction.OnContentChange -> {
                _state.update { it.copy(content = action.value) }
                _saveRequests.trySend(Unit)
            }
            is NoteAction.OnTitleChange -> _state.update { it.copy(currentTitle = action.value) }
        }
    }

    private fun observeAutoSave() {
        viewModelScope.launch {
            _saveRequests.receiveAsFlow()
                .debounce(AUTOSAVE_DEBOUNCE)
                .collect { performSave(false) }
        }
    }

    private fun confirmSave() {
        viewModelScope.launch { performSave(true) }
    }

    private suspend fun performSave(notifySuccess: Boolean) {
        val snapshot = _state.value.toSnapshot() ?: return
        if (snapshot == _lastSaved) {
            if (notifySuccess) _events.send(NoteEvent.Saved)
            return
        }

        runCatching { saveNoteUseCase(snapshot.id, snapshot.title, snapshot.content) }
            .fold(
                onSuccess = { newId ->
                    _lastSaved = snapshot.copy(id = newId)
                    _state.update { it.copy(id = newId) }
                    if (notifySuccess) _events.send(NoteEvent.Saved)
                },
                onFailure = { _events.send(NoteEvent.SaveFailed(it)) }
            )
    }

    private fun NoteState.toSnapshot(): SaveSnapshot? {
        if (id.isBlank()) return null
        return SaveSnapshot(id, currentTitle, content)
    }

    private fun initNoteScreenData(noteId: String?) {
        viewModelScope.launch {
            if (noteId != null) getNote(noteId) else createNote()
        }
    }

    private suspend fun createNote() {
        runCatching { createNoteUseCase() }
            .onSuccess { note ->
                _state.update { it.copy(
                    id = note.id,
                    currentTitle = note.title,
                    originTitle = note.title,
                    content = "",
                    lastModifier = note.lastModified
                ) }
                _lastSaved = SaveSnapshot(note.id, note.title, "")
            }
            .onFailure { _events.send(NoteEvent.SaveFailed(it)) }
    }

    private suspend fun getNote(id: String) {
        runCatching { getNoteUseCase(id) }
            .onSuccess { note ->
                if (note != null)
                    _state.update {
                        it.copy(
                            id = note.id,
                            currentTitle = note.title,
                            originTitle = note.title,
                            content = note.content,
                            lastModifier = note.lastModified
                        )
                    }
            }
            .onFailure {
                _state.update {
                    it.copy(
                        id = "",
                        currentTitle = "",
                        originTitle = "",
                        content = "Error",
                        lastModifier = ""
                    )
                }
            }
    }

    override fun onCleared() {
        super.onCleared()
        val snapshot = _state.value.toSnapshot() ?: return
        if (snapshot == _lastSaved) return

        appScope.scope.launch {
            runCatching { saveNoteUseCase(snapshot.id, snapshot.title, snapshot.content) }
        }
    }
}