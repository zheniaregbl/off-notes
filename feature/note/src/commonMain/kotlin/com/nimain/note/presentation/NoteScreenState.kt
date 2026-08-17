package com.nimain.note.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

internal sealed class NoteScreenState {
    data object Loading : NoteScreenState()
    data class Error(val message: String) : NoteScreenState()
    data class Success(
        val id: String,
        val title: String,
        val content: String,
        val lastModifier: String
    ) : NoteScreenState()
}

@Composable
internal fun NoteScreenState.DisplayResult(
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.Center,
    transitionSpec: AnimatedContentTransitionScope<NoteScreenState>.() -> ContentTransform = {
        fadeIn(tween(durationMillis = 200)) togetherWith ExitTransition.None
    },
    onLoading: @Composable () -> Unit,
    onError: @Composable (NoteScreenState.Error) -> Unit,
    onSuccess: @Composable (NoteScreenState.Success) -> Unit
) {
    AnimatedContent(
        targetState = this,
        transitionSpec = transitionSpec,
        contentKey = { state ->
            when (state) {
                is NoteScreenState.Loading -> 0
                is NoteScreenState.Error -> 1
                is NoteScreenState.Success -> 2
            }
        }
    ) { state ->
        Box(
            modifier = modifier,
            contentAlignment = contentAlignment
        ) {
            when (state) {
                NoteScreenState.Loading -> onLoading()
                is NoteScreenState.Error -> onError(state)
                is NoteScreenState.Success -> onSuccess(state)
            }
        }
    }
}