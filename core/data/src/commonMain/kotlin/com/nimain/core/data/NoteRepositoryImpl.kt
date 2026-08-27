package com.nimain.core.data

import com.nimain.core.data.source.NoteFileDataSource
import com.nimain.core.domain.model.Note
import com.nimain.core.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.collections.emptyList

private const val NOTE_EXTENSION = ".md"
private const val DEFAULT_TITLE = "Untitled"
private const val PREVIEW_LINES = 4

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
        val targetName = "$title.md"
        fileSource.save(id, targetName, content)
        updateCachedNote(id, targetName, content)
        return targetName
    }

    override suspend fun deleteNote(id: String) {
        fileSource.delete(id)
        refresh()
    }

    private fun generateFirstFileName(): String {
        val existsTitles = _notes.value.map { it.title }
        if (DEFAULT_TITLE !in existsTitles) return "$DEFAULT_TITLE$NOTE_EXTENSION"

        var index = 1
        while ("$DEFAULT_TITLE $index" in existsTitles) { index++ }
        return "$DEFAULT_TITLE $index$NOTE_EXTENSION"
    }

    private fun updateCachedNote(oldId: String, newId: String, content: String) {
        val updated = Note(
            id = newId,
            title = newId.dropLast(NOTE_EXTENSION.length),
            content = content.lineSequence().take(PREVIEW_LINES).joinToString("\n"),
            lastModified = ""
        )

        _notes.update { notes ->
            val index = notes.indexOfFirst { it.id == oldId }
            if (index >= 0) notes.toMutableList().apply { set(index, updated) }
            else notes + updated
        }
    }
}