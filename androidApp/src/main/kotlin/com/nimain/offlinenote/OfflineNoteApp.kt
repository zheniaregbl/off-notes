package com.nimain.offlinenote

import android.app.Application
import com.nimain.core.data.di.platformDataModule
import com.nimain.core.di.initKoin
import com.nimain.offlinenote.di.sharedModule
import org.koin.android.ext.koin.androidContext

class OfflineNoteApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin(
            platformModules = listOf(platformDataModule, sharedModule),
            config = { androidContext(this@OfflineNoteApp) }
        )
    }
}