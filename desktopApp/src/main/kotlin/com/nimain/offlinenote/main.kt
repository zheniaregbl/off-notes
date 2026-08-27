package com.nimain.offlinenote

import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.nimain.core.data.di.platformDataModule
import com.nimain.core.di.initKoin
import com.nimain.offlinenote.di.sharedModule

fun main() {
    initKoin(platformModules = listOf(platformDataModule, sharedModule))
    application {
        val windowState = rememberWindowState(
            size = DpSize(1000.dp, 700.dp),
            position = WindowPosition(Alignment.Center)
        )
        Window(
            onCloseRequest = ::exitApplication,
            state = windowState,
            title = "off notes",
        ) { App() }
    }
}