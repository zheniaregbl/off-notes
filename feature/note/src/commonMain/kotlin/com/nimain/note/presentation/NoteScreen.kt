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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.annotation.InternalVoyagerApi
import cafe.adriel.voyager.navigator.internal.BackHandler
import com.nimain.core.extension.defaultScreen
import com.nimain.note.presentation.components.ContentInputField
import com.nimain.note.presentation.components.EditorTopBar

@Composable
internal fun NoteScreen(
    noteId: String?,
    onBack: () -> Unit
) {
    NoteScreenContent(
        modifier = Modifier.defaultScreen(),
        onBack = onBack
    )
}

@OptIn(InternalVoyagerApi::class)
@Composable
internal fun NoteScreenContent(
    modifier: Modifier = Modifier,
    onBack: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    BackHandler(
        enabled = true,
        onBack = onBack
    )

    Box(modifier = modifier.imePadding()) {
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(Modifier.height(10.dp))
            EditorTopBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp),
                onCancel = onBack,
                onConfirm = {}
            )
            Spacer(Modifier.height(16.dp))
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(topStart = 26.dp, topEnd = 26.dp))
                    .background(Color(0xFFF28788))
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 14.dp, vertical = 16.dp)
            ) {
                ContentInputField(
                    modifier = Modifier.fillMaxWidth(),
                    value = content,
                    onValueChange = { content = it },
                    hint = "Input content..."
                )
            }
        }
    }
}