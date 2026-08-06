package com.nimain.offlinenote

import android.app.Application
import com.nimain.core.data.di.platformDataModule
import com.nimain.core.di.initKoin
import com.nimain.offlinenote.di.sharedModule

class OfflineNoteApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin(platformModules = listOf(platformDataModule, sharedModule))
    }
}