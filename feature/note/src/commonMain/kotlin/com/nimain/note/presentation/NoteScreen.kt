package com.nimain.note.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nimain.note.presentation.components.NoteScreenContent
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
internal fun NoteScreen(
    noteId: String?,
    onBack: () -> Unit
) {
    val viewModel: NoteViewModel = koinViewModel<NoteViewModel> { parametersOf(noteId) }
    val state = viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is NoteEvent.SaveFailed -> { }
                NoteEvent.Saved -> onBack()
            }
        }
    }

    NoteScreenContent(
        state = state,
        onAction = viewModel::onAction,
        onBack = onBack
    )
}
