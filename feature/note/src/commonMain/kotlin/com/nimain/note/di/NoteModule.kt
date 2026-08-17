package com.nimain.note.di

import com.nimain.core.navigation.NavGraphProvider
import com.nimain.note.navigation.NoteNavGraphProvider
import com.nimain.note.presentation.NoteViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val noteModule = module {
    single<NavGraphProvider>(named("note")) { NoteNavGraphProvider() }
    viewModel { (noteId: String?) -> NoteViewModel(noteId, get(), get(), get()) }
}