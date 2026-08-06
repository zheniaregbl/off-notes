package com.nimain.core.data.di

import com.nimain.core.data.source.NoteFileDataSource
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformDataModule: Module = module {
    single { NoteFileDataSource() }
}