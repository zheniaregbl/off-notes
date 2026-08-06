package com.nimain.home.di

import com.nimain.core.navigation.NavGraphProvider
import com.nimain.home.navigation.HomeNavGraphProvider
import org.koin.core.qualifier.named
import org.koin.dsl.module

val homeModule = module {
    single<NavGraphProvider>(named("home")) { HomeNavGraphProvider() }
}