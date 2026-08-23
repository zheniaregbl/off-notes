package com.nimain.core.presentation.util.hsl

object NoteColorGenerator {
    private const val SATURATION = 0.7f
    private const val LIGHTNESS = 0.87f

    fun generate(title: String): NoteHslColor {
        val hash = stableHash(title)
        val hue = ((hash % 360) + 360) % 360
        return NoteHslColor(hue.toFloat(), SATURATION, LIGHTNESS)
    }

    private fun stableHash(input: String): Int {
        var hash = 0
        for (char in input) {
            hash = 31 * hash + char.code
        }
        return hash
    }
}