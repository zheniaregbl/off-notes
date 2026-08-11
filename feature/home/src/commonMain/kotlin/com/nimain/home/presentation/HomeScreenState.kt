package com.nimain.home.presentation

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
import com.nimain.home.presentation.model.NoteUiModel

internal sealed class HomeScreenState {
    data object Loading : HomeScreenState()
    data object Empty : HomeScreenState()
    data class Error(val message: String) : HomeScreenState()
    data class Success(
        val notes: List<NoteUiModel>,
        val searchQuery: String,
        val isRefreshing: Boolean = false,
        val errorMessage: String? = null
    ) : HomeScreenState()
}

@Composable
internal fun HomeScreenState.DisplayResult(
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.Center,
    transitionSpec: AnimatedContentTransitionScope<HomeScreenState>.() -> ContentTransform = {
        fadeIn(tween(durationMillis = 200)) togetherWith ExitTransition.None
    },
    onLoading: @Composable () -> Unit,
    onEmpty: @Composable () -> Unit,
    onError: @Composable (HomeScreenState.Error) -> Unit,
    onSuccess: @Composable (HomeScreenState.Success) -> Unit
) {
    AnimatedContent(
        targetState = this,
        transitionSpec = transitionSpec,
        contentKey = { state ->
            when (state) {
                is HomeScreenState.Loading -> 0
                is HomeScreenState.Empty -> 1
                is HomeScreenState.Error -> 2
                is HomeScreenState.Success -> 3
            }
        }
    ) { state ->
        Box(
            modifier = modifier,
            contentAlignment = contentAlignment
        ) {
            when (state) {
                HomeScreenState.Loading -> onLoading()
                HomeScreenState.Empty -> onEmpty()
                is HomeScreenState.Error -> onError(state)
                is HomeScreenState.Success -> onSuccess(state)
            }
        }
    }
}
