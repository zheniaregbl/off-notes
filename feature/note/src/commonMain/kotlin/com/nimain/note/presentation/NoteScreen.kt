package com.nimain.note.presentation

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nimain.note.presentation.components.NoteScreenContent
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
internal fun NoteScreen(
    noteId: String?,
    onConfirm: () -> Unit,
    onBack: () -> Unit
) {
    val viewModel: NoteViewModel = koinViewModel<NoteViewModel> { parametersOf(noteId) }
    val state = viewModel.state.collectAsStateWithLifecycle()

    NoteScreenContent(
        state = state,
        onAction = {
            viewModel.onAction(it)
            if (it is NoteAction.OnConfirm) onConfirm()
        },
        onBack = onBack
    )
}
