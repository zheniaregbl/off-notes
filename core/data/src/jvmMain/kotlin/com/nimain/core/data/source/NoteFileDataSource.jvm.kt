package com.nimain.core.data.source

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

actual class NoteFileDataSource {

    private val notesDir: File by lazy {
        File(System.getProperty("user.home"), ".offlinenote/notes").apply { mkdirs() }
    }

    actual suspend fun listFiles(): List<PlatformFile> = withContext(Dispatchers.IO) {
        notesDir.listFiles { f -> f.extension == "md" }
            ?.map { PlatformFile(it.name, it.lastModified()) } ?: emptyList()
    }

    actual suspend fun read(fileName: String): String = withContext(Dispatchers.IO) {
        File(notesDir, fileName).readText()
    }

    actual suspend fun write(fileName: String, content: String) = withContext(Dispatchers.IO) {
        File(notesDir, fileName).writeText(content)
    }

    actual suspend fun delete(fileName: String) {
        withContext(Dispatchers.IO) {
            File(notesDir, fileName).delete()
        }
    }
}