package com.nimain.core.data.source

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption
import java.nio.file.StandardOpenOption
import java.util.stream.Collectors

actual class NoteFileDataSource(private val notesDirPath: Path) {
    actual suspend fun listFiles(): List<PlatformFile> =
        withContext(Dispatchers.IO) {
            listFilesInternal()
        }

    private fun listFilesInternal(): List<PlatformFile> {
        if (!Files.exists(notesDirPath)) return emptyList()

        Files.list(notesDirPath).use { stream ->
            return stream
                .filter { path ->
                    Files.isRegularFile(path) && path.fileName.toString().endsWith(".md")
                }
                .map { path ->
                    PlatformFile(
                        fileName = path.fileName.toString(),
                        lastModified = Files.getLastModifiedTime(path).toMillis()
                    )
                }
                .collect(Collectors.toList())
        }
    }

    actual suspend fun read(fileName: String): String = withContext(Dispatchers.IO) {
        Files.newBufferedReader(
            notesDirPath.resolve(fileName),
            Charsets.UTF_8
        ).use { reader ->
            reader.readText()
        }
    }

    actual suspend fun save(oldFileName: String, newFileName: String, content: String) =
        withContext(Dispatchers.IO) {
            if (oldFileName == newFileName)
                write(newFileName, content)
            else
                rename(oldFileName, newFileName, content)
        }

    private fun write(fileName: String, content: String) {
        val target = notesDirPath.resolve(fileName)
        val temp = notesDirPath.resolve("$fileName.tmp")

        try {
            Files.newBufferedWriter(
                temp,
                Charsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING,
                StandardOpenOption.WRITE
            ).use { writer ->
                writer.write(content)
            }

            Files.move(
                temp,
                target,
                StandardCopyOption.ATOMIC_MOVE,
                StandardCopyOption.REPLACE_EXISTING
            )
        } finally {
            Files.deleteIfExists(temp)
        }
    }

    private fun rename(oldFileName: String, newFileName: String, content: String) {
        val oldFile = notesDirPath.resolve(oldFileName)
        val newFile = notesDirPath.resolve(newFileName)
        val tempFile = notesDirPath.resolve("$newFileName.tmp")

        try {
            Files.newBufferedWriter(
                tempFile,
                Charsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING,
                StandardOpenOption.WRITE
            ).use { writer ->
                writer.write(content)
            }

            Files.move(
                tempFile,
                newFile,
                StandardCopyOption.ATOMIC_MOVE
            )

            Files.deleteIfExists(oldFile)
        } finally {
            Files.deleteIfExists(tempFile)
        }
    }

    actual suspend fun delete(fileName: String) {
        withContext(Dispatchers.IO) {
            Files.deleteIfExists(
                notesDirPath.resolve(fileName)
            )
        }
    }
}