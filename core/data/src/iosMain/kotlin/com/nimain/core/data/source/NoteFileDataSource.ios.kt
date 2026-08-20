package com.nimain.core.data.source

import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDate
import platform.Foundation.NSFileManager
import platform.Foundation.NSFileType
import platform.Foundation.NSFileTypeRegular
import platform.Foundation.NSString
import platform.Foundation.NSURL
import platform.Foundation.create
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.stringWithContentsOfFile
import platform.Foundation.timeIntervalSince1970
import platform.Foundation.writeToFile

@OptIn(ExperimentalForeignApi::class)
actual class NoteFileDataSource(private val notesDirPath: String) {

    private val fileManager = NSFileManager.defaultManager

    actual suspend fun listFiles(): List<PlatformFile> {
        if (!fileManager.fileExistsAtPath(notesDirPath)) return emptyList()

        val files = fileManager.contentsOfDirectoryAtPath(
            notesDirPath,
            error = null
        ) ?: return emptyList()

        return files
            .filterIsInstance<String>()
            .filter { fileName ->
                fileName.endsWith(".md") && isRegularFile(fileName)
            }
            .mapNotNull { fileName ->
                val path = "$notesDirPath/$fileName"

                val attributes = fileManager.attributesOfItemAtPath(
                    path,
                    error = null
                ) ?: return@mapNotNull null

                val modifiedDate = attributes["NSFileModificationDate"] as? NSDate
                    ?: return@mapNotNull null

                PlatformFile(
                    fileName = fileName,
                    lastModified = (modifiedDate.timeIntervalSince1970 * 1000).toLong()
                )
            }
    }

    actual suspend fun read(fileName: String, linesLimit: Int): String {
        val path = "$notesDirPath/$fileName"
        val content = NSString.stringWithContentsOfFile(
            path = path,
            encoding = NSUTF8StringEncoding,
            error = null
        ) ?: return ""

        if (linesLimit <= 0) return content

        return content
            .split("\n")
            .take(linesLimit)
            .joinToString("\n")
    }

    actual suspend fun save(oldFileName: String, newFileName: String, content: String) {
        if (oldFileName == newFileName) {
            write(newFileName, content)
        } else {
            rename(
                oldFileName = oldFileName,
                newFileName = newFileName,
                content = content
            )
        }
    }

    @OptIn(BetaInteropApi::class)
    private fun write(
        fileName: String,
        content: String
    ) {
        val target = "$notesDirPath/$fileName"
        val temp = "$target.tmp"

        try {
            NSString.create(string = content).writeToFile(
                path = temp,
                atomically = true,
                encoding = NSUTF8StringEncoding,
                error = null
            )

            val targetUrl = NSURL.fileURLWithPath(target)
            val tempUrl = NSURL.fileURLWithPath(temp)

            if (fileManager.fileExistsAtPath(target)) {
                fileManager.replaceItemAtURL(
                    originalItemURL = targetUrl,
                    withItemAtURL = tempUrl,
                    backupItemName = null,
                    options = 0u,
                    resultingItemURL = null,
                    error = null
                )
            } else {
                fileManager.moveItemAtURL(
                    tempUrl,
                    toURL = targetUrl,
                    error = null
                )
            }
        } finally {
            fileManager.removeItemAtPath(
                temp,
                error = null
            )
        }
    }

    @OptIn(BetaInteropApi::class)
    private fun rename(
        oldFileName: String,
        newFileName: String,
        content: String
    ) {
        val oldPath = "$notesDirPath/$oldFileName"
        val newPath = "$notesDirPath/$newFileName"
        val tempPath = "$newPath.tmp"

        try {
            NSString.create(string = content).writeToFile(
                path = tempPath,
                atomically = true,
                encoding = NSUTF8StringEncoding,
                error = null
            )

            val newUrl = NSURL.fileURLWithPath(newPath)
            val tempUrl = NSURL.fileURLWithPath(tempPath)

            if (fileManager.fileExistsAtPath(newPath)) {
                fileManager.replaceItemAtURL(
                    originalItemURL = newUrl,
                    withItemAtURL = tempUrl,
                    backupItemName = null,
                    options = 0u,
                    resultingItemURL = null,
                    error = null
                )
            } else {
                fileManager.moveItemAtURL(
                    tempUrl,
                    toURL = newUrl,
                    error = null
                )
            }

            fileManager.removeItemAtPath(
                oldPath,
                error = null
            )
        } finally {
            fileManager.removeItemAtPath(
                tempPath,
                error = null
            )
        }
    }

    actual suspend fun delete(fileName: String) {
        fileManager.removeItemAtPath(
            "$notesDirPath/$fileName",
            error = null
        )
    }

    private fun isRegularFile(fileName: String): Boolean {
        val path = "$notesDirPath/$fileName"
        val attributes = fileManager.attributesOfItemAtPath(
            path = path,
            error = null
        ) ?: return false

        return attributes[NSFileType] == NSFileTypeRegular
    }
}