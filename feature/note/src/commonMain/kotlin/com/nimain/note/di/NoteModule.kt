package com.nimain.note.di

import com.nimain.core.navigation.NavGraphProvider
import com.nimain.note.navigation.NoteNavGraphProvider
import org.koin.core.qualifier.named
import org.koin.dsl.module

val noteModule = module {
    single<NavGraphProvider>(named("note")) { NoteNavGraphProvider() }
}