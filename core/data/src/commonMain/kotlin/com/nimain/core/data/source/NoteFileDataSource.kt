package com.nimain.core.data.source

expect class NoteFileDataSource {
    suspend fun listFiles(): List<PlatformFile>
    suspend fun read(fileName: String): String
    suspend fun save(oldFileName: String, newFileName: String, content: String)
    suspend fun delete(fileName: String)
}

data class PlatformFile(val fileName: String, val lastModified: Long)