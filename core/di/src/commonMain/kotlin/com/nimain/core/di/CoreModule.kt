package com.nimain.core.di

import com.nimain.core.domain.util.AppScope
import org.koin.dsl.module

val coreModule = module {
    single { AppScope() }
}