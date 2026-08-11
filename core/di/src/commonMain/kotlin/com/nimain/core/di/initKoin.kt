package com.nimain.core.di

import com.nimain.core.data.di.dataModule
import com.nimain.core.domain.di.domainModule
import com.nimain.home.di.homeModule
import com.nimain.note.di.noteModule
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module

fun initKoin(
    platformModules: List<Module> = emptyList(),
    config: (KoinApplication.() -> Unit)? = null
) {
    startKoin {
        config?.invoke(this)
        modules(domainModule, dataModule, homeModule, noteModule)
        modules(platformModules)
    }
}