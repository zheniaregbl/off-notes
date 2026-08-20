package com.nimain.core.data.di

import com.nimain.core.data.source.NoteFileDataSource
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSSearchPathForDirectoriesInDomains
import platform.Foundation.NSUserDomainMask

actual val platformDataModule = module {
    single { NoteFileDataSource(notesDirectoryPath()) }
}

@OptIn(ExperimentalForeignApi::class)
private fun notesDirectoryPath(): String {
    val documentsDirectory =
        NSSearchPathForDirectoriesInDomains(
            NSDocumentDirectory,
            NSUserDomainMask,
            true
        ).first() as String

    val notesDirectory = "$documentsDirectory/notes"

    NSFileManager.defaultManager.createDirectoryAtPath(
        path = notesDirectory,
        withIntermediateDirectories = true,
        attributes = null,
        error = null
    )

    return notesDirectory
}