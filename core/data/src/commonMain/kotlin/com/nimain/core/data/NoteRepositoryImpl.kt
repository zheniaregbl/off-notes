package com.nimain.core.data

import com.nimain.core.data.source.NoteFileDataSource
import com.nimain.core.domain.model.Note
import com.nimain.core.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.collections.emptyList
import kotlin.time.Clock

class NoteRepositoryImpl(
    private val fileSource: NoteFileDataSource
) : NoteRepository {
    private val _notes = MutableStateFlow<List<Note>>(emptyList())

    override fun observeNotes(): Flow<List<Note>> = _notes.asStateFlow()

    override suspend fun refresh() {
        _notes.value = fileSource.listFiles().map { pf ->
            val content = fileSource.read(pf.fileName)
            Note(
                id = pf.fileName,
                title = content.lineSequence().firstOrNull()?.removePrefix("# ") ?: pf.fileName,
                content = content,
                lastModified = pf.lastModified.toString()
            )
        }
    }

    override suspend fun getNote(id: String): Note? {
        return _notes.value.find { it.id == id }
            ?: runCatching {
                val content = fileSource.read(id)
                Note(id = id, title = id, content = content, lastModified = 0L.toString())
            }.getOrNull()
    }

    override suspend fun createNote(): Note {
        val fileName = "note_${Clock.System.now().toEpochMilliseconds()}.md"
        fileSource.write(fileName, "")
        refresh()
        return getNote(fileName)!!
    }

    override suspend fun saveNote(id: String, content: String) {
        fileSource.write(id, content)
        refresh()
    }

    override suspend fun deleteNote(id: String) {
        fileSource.delete(id)
        refresh()
    }
}