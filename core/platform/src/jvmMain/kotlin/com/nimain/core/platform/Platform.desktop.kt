package com.nimain.core.platform

actual val platformType = PlatformType.Desktop

enum class DesktopOs {
    Windows,
    MacOS,
    Linux,
    Unknown
}

val desktopOs: DesktopOs
    get() {
        val os = System.getProperty("os.name").lowercase()
        return when {
            os.contains("win") -> DesktopOs.Windows
            os.contains("mac") -> DesktopOs.MacOS
            os.contains("linux") -> DesktopOs.Linux
            else -> DesktopOs.Unknown
        }
    }