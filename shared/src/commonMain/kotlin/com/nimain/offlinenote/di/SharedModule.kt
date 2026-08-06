package com.nimain.offlinenote.di

import com.nimain.offlinenote.navigation.NavGraphRegistry
import org.koin.dsl.module

val sharedModule = module { single { NavGraphRegistry(getAll()) } }