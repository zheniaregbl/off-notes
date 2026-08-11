package com.nimain.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nimain.core.domain.usecase.DeleteNoteUseCase
import com.nimain.core.domain.usecase.GetNotesUseCase
import com.nimain.core.domain.usecase.RefreshNotesUseCase
import com.nimain.home.presentation.mapper.toUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class HomeViewModel(
    private val getNotesUseCase: GetNotesUseCase,
    private val refreshNotesUseCase: RefreshNotesUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeScreenState> = _state
        .map { it.toUiState() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = HomeState().toUiState()
        )

    init {
        observeNotes()
        refresh()
    }

    fun onAction(action: HomeAction) {
        when (action) {
            is HomeAction.OnSearchChange -> _state.update { it.copy(searchQuery = action.query) }
            is HomeAction.OnDeleteNote -> deleteNote(action.id)
            HomeAction.OnRefreshNotes -> refresh()
        }
    }

    private fun observeNotes() {
        viewModelScope.launch {
            getNotesUseCase().collect { notes ->
                _state.update { current ->
                    current.copy(allNotes = notes.map { it.toUiModel() })
                }
            }
        }
    }

    private fun refresh() {
        viewModelScope.launch {
            _state.update { it.copy(isRefreshing = true) }
            runCatching { refreshNotesUseCase() }
                .onSuccess {
                    _state.update { it.copy(errorMessage = null) }
                }
                .onFailure { e ->
                    _state.update { it.copy(errorMessage = e.message ?: "Couldn't refresh notes") }
                }
            _state.update { it.copy(hasLoadedOnce = true, isRefreshing = false) }
        }
    }

    private fun deleteNote(id: String) {
        viewModelScope.launch {
            runCatching { deleteNoteUseCase(id) }
                .onFailure { e ->
                    _state.update { it.copy(errorMessage = e.message ?: "Couldn't delete note") }
                }
        }
    }
}