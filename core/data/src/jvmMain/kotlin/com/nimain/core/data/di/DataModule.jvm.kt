package com.nimain.core.data.di

import com.nimain.core.data.source.NoteFileDataSource
import org.koin.core.module.Module
import org.koin.dsl.module
import java.nio.file.Files
import java.nio.file.Paths

actual val platformDataModule: Module = module {
    single {
        val notesDir = Paths.get(
            System.getProperty("user.home"),
            "Offline-note",
            "notes"
        )

        Files.createDirectories(notesDir)

        NoteFileDataSource(
            notesDirPath = notesDir
        )
    }
}