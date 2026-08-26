package com.nimain.note.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.nimain.core.presentation.theme.BackgroundColor
import com.nimain.core.presentation.util.hsl.NoteColorGenerator
import com.nimain.core.presentation.util.hsl.toComposeColor
import com.nimain.note.presentation.DisplayResult
import com.nimain.note.presentation.NoteAction
import com.nimain.note.presentation.NoteScreenState

@Composable
internal actual fun NoteScreenContent(
    modifier: Modifier,
    state: State<NoteScreenState>,
    onAction: (NoteAction) -> Unit,
    onBack: () -> Unit
) {
    NoteOverlay(onDismiss = onBack) {
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
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(topStart = 26.dp, topEnd = 26.dp))
                            .background(
                                NoteColorGenerator
                                    .generate(success.originTitle)
                                    .toComposeColor()
                            )
                            .verticalScroll(rememberScrollState()),
                    ) {
                        TitleInputField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp, vertical = 16.dp)
                                .onFocusChanged { focus ->
                                    if (!focus.isFocused) onAction(NoteAction.OnTitleFocusLost)
                                },
                            value = success.currentTitle,
                            onValueChange = { onAction(NoteAction.OnTitleChange(it)) }
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 20.dp, vertical = 16.dp)
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

@Composable
private fun NoteOverlay(
    onDismiss: () -> Unit,
    content: @Composable () -> Unit
) {
    BoxWithConstraints(
        Modifier.fillMaxSize()
            .pointerInput(Unit) { detectTapGestures { onDismiss() } },
        contentAlignment = Alignment.Center
    ) {
        val compact = maxWidth < 600.dp
        Surface(
            modifier = if (compact) Modifier.fillMaxSize()
            else Modifier.fillMaxWidth(.6f).widthIn(max = 640.dp)
                .heightIn(max = 560.dp)
                .pointerInput(Unit) { detectTapGestures { } },
            color = Color.Transparent,
            shape = if (compact) RectangleShape else MaterialTheme.shapes.extraLarge,
        ) { content() }
    }
}