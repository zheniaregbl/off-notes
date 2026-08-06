package com.nimain.core.domain.di

import com.nimain.core.domain.usecase.GetNotesUseCase
import com.nimain.core.domain.usecase.SaveNoteUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { GetNotesUseCase(get()) }
    factory { SaveNoteUseCase(get()) }
}