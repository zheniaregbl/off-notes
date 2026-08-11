package com.nimain.core.domain.di

import com.nimain.core.domain.usecase.CreateNoteUseCase
import com.nimain.core.domain.usecase.DeleteNoteUseCase
import com.nimain.core.domain.usecase.GetNoteUseCase
import com.nimain.core.domain.usecase.GetNotesUseCase
import com.nimain.core.domain.usecase.RefreshNotesUseCase
import com.nimain.core.domain.usecase.SaveNoteUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { GetNotesUseCase(get()) }
    factory { GetNoteUseCase(get()) }
    factory { SaveNoteUseCase(get()) }
    factory { CreateNoteUseCase(get()) }
    factory { DeleteNoteUseCase(get()) }
    factory { RefreshNotesUseCase(get()) }
}