package com.nimain.note.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nimain.core.extension.defaultScreen
import com.nimain.core.presentation.theme.BackgroundColor
import com.nimain.note.presentation.components.ContentInputField
import com.nimain.note.presentation.components.EditorTopBar
import com.nimain.note.presentation.components.TitleInputField
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
        modifier = Modifier.defaultScreen(),
        state = state,
        onAction = {
            viewModel.onAction(it)
            if (it is NoteAction.OnConfirm) onConfirm()
        },
        onBack = onBack
    )
}

@Composable
internal fun NoteScreenContent(
    modifier: Modifier = Modifier,
    state: State<NoteScreenState>,
    onAction: (NoteAction) -> Unit ,
    onBack: () -> Unit
) {
    Box(modifier = modifier.imePadding()) {
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(Modifier.height(10.dp))
            EditorTopBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp),
                onCancel = onBack,
                onConfirm = { onAction(NoteAction.OnConfirm) }
            )
            Spacer(Modifier.height(16.dp))
            state.value.DisplayResult(
                modifier = Modifier
                    .fillMaxSize(),
                onLoading = { CircularProgressIndicator(color = BackgroundColor) },
                onError = {  },
                onSuccess = { success ->
                    Column(modifier = Modifier.fillMaxSize()) {
                        TitleInputField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 14.dp),
                            value = success.title,
                            onValueChange = { onAction(NoteAction.OnTitleChange(it)) }
                        )
                        Spacer(Modifier.height(16.dp))
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(topStart = 26.dp, topEnd = 26.dp))
                                .background(Color(0xFFF28788))
                                .verticalScroll(rememberScrollState())
                                .padding(horizontal = 14.dp, vertical = 16.dp)
                        ) {
                            ContentInputField(
                                modifier = Modifier.fillMaxWidth(),
                                value = success.content,
                                onValueChange = { onAction(NoteAction.OnContentChange(it)) },
                                hint = "Input content..."
                            )
                        }
                    }
                }
            )
        }
    }
}