package com.nimain.offlinenote

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.nimain.core.data.di.platformDataModule
import com.nimain.core.di.initKoin
import com.nimain.offlinenote.di.sharedModule

fun main() {
    initKoin(platformModules = listOf(platformDataModule, sharedModule))
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "Offlinenote",
        ) {
            App()
        }
    }
}