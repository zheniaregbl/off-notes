package com.nimain.core.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically

fun slideInFromEnd(duration: Int = 180): EnterTransition =
    slideInHorizontally(tween(duration)) { it } + fadeIn(tween(duration))

fun slideOutToStart(duration: Int = 180): ExitTransition =
    slideOutHorizontally(tween(duration)) { -it / 3 } + fadeOut(tween(duration))

private const val FADE_DURATION = 220

fun fadeInWithScale(): EnterTransition =
    fadeIn(
        animationSpec = tween(FADE_DURATION, easing = FastOutSlowInEasing)
    ) + scaleIn(
        initialScale = 0.96f,
        animationSpec = tween(FADE_DURATION, easing = FastOutSlowInEasing)
    )

fun fadeOutWithScale(): ExitTransition =
    fadeOut(
        animationSpec = tween(FADE_DURATION, easing = FastOutSlowInEasing)
    ) + scaleOut(
        targetScale = 1.04f,
        animationSpec = tween(FADE_DURATION, easing = FastOutSlowInEasing)
    )

private const val MODAL_DURATION = 280

fun slideUpModal(): EnterTransition =
    slideInVertically(
        initialOffsetY = { fullHeight -> fullHeight },
        animationSpec = tween(MODAL_DURATION, easing = FastOutSlowInEasing)
    ) + fadeIn(tween(MODAL_DURATION))

fun slideDownModal(): ExitTransition =
    slideOutVertically(
        targetOffsetY = { fullHeight -> fullHeight },
        animationSpec = tween(MODAL_DURATION, easing = FastOutSlowInEasing)
    ) + fadeOut(tween(MODAL_DURATION))