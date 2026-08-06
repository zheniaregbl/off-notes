package com.nimain.home.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import offlinenote.feature.home.generated.resources.Res
import offlinenote.feature.home.generated.resources.star_svg
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun TagSection(
    modifier: Modifier = Modifier,
    horizontal: Arrangement.Horizontal,
    vertical: Arrangement.Vertical,
    tags: List<String> =
        listOf("Recent", "All", "Starred", "Tasks", "Work")
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = horizontal,
        verticalArrangement = vertical
    ) {
        tags.forEach {
            Chip(
                modifier = Modifier.wrapContentWidth(),
                text = it,
                isSelected = false,
                onClick = {}
            )
        }
    }
}