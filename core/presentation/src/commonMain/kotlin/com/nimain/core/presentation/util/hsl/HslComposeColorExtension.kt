package com.nimain.core.presentation.util.hsl

import androidx.compose.ui.graphics.Color

fun NoteHslColor.toComposeColor(): Color {
    val (r, g, b) = hslToRgb(hue, saturation, lightness)
    return Color(r, g, b)
}

private fun hslToRgb(h: Float, s: Float, l: Float): Triple<Float, Float, Float> {
    if (s == 0f) return Triple(l, l, l)

    val q = if (l < 0.5f) l * (1 + s) else l + s - l * s
    val p = 2 * l - q
    val hk = h / 360f

    fun hueToRgb(t: Float): Float {
        var tt = t
        if (tt < 0f) tt += 1f
        if (tt > 1f) tt -= 1f
        return when {
            tt < 1f / 6f -> p + (q - p) * 6f * tt
            tt < 1f / 2f -> q
            tt < 2f / 3f -> p + (q - p) * (2f / 3f - tt) * 6f
            else -> p
        }
    }

    val r = hueToRgb(hk + 1f / 3f)
    val g = hueToRgb(hk)
    val b = hueToRgb(hk - 1f / 3f)
    return Triple(r, g, b)
}