package com.nimain.core.di

import com.nimain.core.domain.di.domainModule
import com.nimain.home.di.homeModule
import com.nimain.note.di.noteModule
import org.koin.core.context.startKoin
import org.koin.core.module.Module

fun initKoin(platformModules: List<Module> = emptyList()) {
    startKoin {
        modules(domainModule, homeModule, noteModule)
        modules(platformModules)
    }
}