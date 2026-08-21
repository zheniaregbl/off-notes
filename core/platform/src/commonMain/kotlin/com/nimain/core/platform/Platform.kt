package com.nimain.core.platform

enum class PlatformType {
    Mobile,
    Desktop
}

expect val platformType: PlatformType