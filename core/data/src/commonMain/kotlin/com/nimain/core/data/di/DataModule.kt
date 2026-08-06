package com.nimain.core.data.di

import com.nimain.core.data.NoteRepositoryImpl
import com.nimain.core.domain.repository.NoteRepository
import org.koin.core.module.Module
import org.koin.dsl.module

val dataModule = module { single<NoteRepository> { NoteRepositoryImpl(get()) } }

expect val platformDataModule: Module