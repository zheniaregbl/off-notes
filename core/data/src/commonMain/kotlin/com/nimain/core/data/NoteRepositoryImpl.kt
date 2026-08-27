package com.nimain.core.data

import com.nimain.core.data.source.NoteFileDataSource
import com.nimain.core.domain.model.Note
import com.nimain.core.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.collections.emptyList
import kotlin.time.Clock

private const val NOTE_EXTENSION = ".md"
private const val PREVIEW_LINES = 4
private const val MAX_TITLE_LENGTH = 100
private val SEPARATOR_CHARS = charArrayOf('/', '\\', ':', '|')
private val FORBIDDEN_CHARS = charArrayOf('*', '?', '"', '<', '>')
private val RESERVED_NAMES = setOf(
    "CON", "PRN", "AUX", "NUL",
    "COM1", "COM2", "COM3", "COM4", "COM5", "COM6", "COM7", "COM8", "COM9",
    "LPT1", "LPT2", "LPT3", "LPT4", "LPT5", "LPT6", "LPT7", "LPT8", "LPT9"
)
internal const val DEFAULT_TITLE = "Untitled"

class NoteRepositoryImpl(private val fileSource: NoteFileDataSource) : NoteRepository {
    private val _notes = MutableStateFlow<List<Note>>(emptyList())

    override fun observeNotes(): Flow<List<Note>> = _notes.asStateFlow()

    override suspend fun refresh() {
        _notes.value = fileSource.listFiles().map { pf ->
            val preview = fileSource.read(pf.fileName, PREVIEW_LINES)
            Note(
                id = pf.fileName,
                title = pf.fileName.dropLast(NOTE_EXTENSION.length),
                content = preview,
                lastModified = pf.lastModified.toString()
            )
        }
    }

    override suspend fun getNote(id: String): Note? = runCatching {
        Note(
            id = id,
            title = id.dropLast(NOTE_EXTENSION.length),
            content = fileSource.read(id),
            lastModified = _notes.value.find { it.id == id }?.lastModified.orEmpty()
        )
    }.getOrNull()

    override suspend fun createNote(): Note {
        refresh()
        val fileName = generateFirstFileName()
        fileSource.save(fileName, fileName, "")
        refresh()
        return getNote(fileName)!!
    }

    override suspend fun saveNote(id: String, title: String, content: String): String {
        val safeTitle = sanitizeTitle(title).ifBlank { DEFAULT_TITLE }
        val desiredName = "$safeTitle$NOTE_EXTENSION"
        val targetName = if (desiredName == id) id else uniqueFileName(desiredName, id)

        fileSource.save(id, targetName, content)
        updateCachedNote(id, targetName, content)
        return targetName
    }

    override suspend fun deleteNote(id: String) {
        fileSource.delete(id)
        refresh()
    }

    private fun updateCachedNote(oldId: String, newId: String, content: String) {
        val updated = Note(
            id = newId,
            title = newId.dropLast(NOTE_EXTENSION.length),
            content = content.lineSequence().take(PREVIEW_LINES).joinToString("\n"),
            lastModified = Clock.System.now().toEpochMilliseconds().toString()
        )

        _notes.update { notes ->
            val index = notes.indexOfFirst { it.id == oldId }
            if (index >= 0) notes.toMutableList().apply { set(index, updated) }
            else notes + updated
        }
    }

    private fun sanitizeTitle(raw: String): String {
        val cleaned = buildString {
            for (char in raw) {
                when {
                    char in SEPARATOR_CHARS -> append(' ')
                    char in FORBIDDEN_CHARS -> Unit
                    char.isISOControl() -> append(' ')
                    else -> append(char)
                }
            }
        }
            .replace(Regex("\\s+"), " ")
            .trim()
            .trimEnd('.')
            .take(MAX_TITLE_LENGTH)
            .trim()

        if (cleaned.isBlank()) return DEFAULT_TITLE
        if (cleaned.uppercase() in RESERVED_NAMES) return "${cleaned}_"

        return cleaned
    }

    private fun uniqueFileName(fileName: String, ignore: String?): String {
        val taken = _notes.value.mapTo(mutableSetOf()) { it.id } - setOfNotNull(ignore)
        if (fileName !in taken) return fileName

        val base = fileName.dropLast(NOTE_EXTENSION.length)
        var index = 1
        while ("$base $index$NOTE_EXTENSION" in taken) index++
        return "$base $index$NOTE_EXTENSION"
    }

    private fun generateFirstFileName(): String =
        uniqueFileName("$DEFAULT_TITLE$NOTE_EXTENSION", null)
}