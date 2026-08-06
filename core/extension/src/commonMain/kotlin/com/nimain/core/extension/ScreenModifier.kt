package com.nimain.core.extension

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.ui.Modifier

fun Modifier.defaultScreen() = Modifier
    .fillMaxSize()
    .statusBarsPadding()