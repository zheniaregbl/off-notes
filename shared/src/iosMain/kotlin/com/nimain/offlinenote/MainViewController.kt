package com.nimain.offlinenote

import androidx.compose.ui.window.ComposeUIViewController
import com.nimain.core.data.di.platformDataModule
import com.nimain.core.di.initKoin
import com.nimain.offlinenote.di.sharedModule

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin(platformModules = listOf(platformDataModule, sharedModule))
    }
) { App() }