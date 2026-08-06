package com.nimain.core.data.source

import platform.Foundation.NSFileManager
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSUserDomainMask
import platform.Foundation.NSURL
import platform.Foundation.writeToFile
import platform.Foundation.stringWithContentsOfFile
import platform.Foundation.NSString
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDate
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.timeIntervalSince1970

@OptIn(ExperimentalForeignApi::class)
actual class NoteFileDataSource {
    private val notesDir: String by lazy {
        val docsUrl = NSFileManager.defaultManager.URLsForDirectory(
            NSDocumentDirectory, NSUserDomainMask
        ).first() as NSURL
        val path = docsUrl.path!! + "/notes"
        NSFileManager.defaultManager.createDirectoryAtPath(path, true, null, null)
        path
    }

    actual suspend fun listFiles(): List<PlatformFile> {
        val fm = NSFileManager.defaultManager
        val items = fm.contentsOfDirectoryAtPath(notesDir, null) as? List<String> ?: emptyList()
        return items.filter { it.endsWith(".md") }.map { name ->
            val attrs = fm.attributesOfItemAtPath("$notesDir/$name", null)
            val modified = (attrs?.get("NSFileModificationDate") as? NSDate)
                ?.timeIntervalSince1970?.toLong()?.times(1000) ?: 0L
            PlatformFile(name, modified)
        }
    }

    actual suspend fun read(fileName: String): String {
        return NSString.stringWithContentsOfFile("$notesDir/$fileName", NSUTF8StringEncoding, null) ?: ""
    }

    actual suspend fun write(fileName: String, content: String) {
        (content as NSString).writeToFile("$notesDir/$fileName", true, NSUTF8StringEncoding, null)
    }

    actual suspend fun delete(fileName: String) {
        NSFileManager.defaultManager.removeItemAtPath("$notesDir/$fileName", null)
    }
}