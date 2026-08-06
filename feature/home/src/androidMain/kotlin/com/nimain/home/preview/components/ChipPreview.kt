package com.nimain.home.preview.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nimain.core.presentation.theme.BackgroundColor
import com.nimain.home.presentation.components.Chip
import offlinenote.feature.home.generated.resources.Res
import offlinenote.feature.home.generated.resources.star_svg
import org.jetbrains.compose.resources.painterResource

@Composable
@Preview
private fun SelectedChipPreview() {
    Box(
        modifier = Modifier
            .background(BackgroundColor)
            .padding(10.dp)
    ) {
        Chip(
            text = "Recent",
            isSelected = true,
            onClick = {}
        )
    }
}

@Composable
@Preview
private fun NotSelectedChipPreview() {
    Box(
        modifier = Modifier
            .background(BackgroundColor)
            .padding(10.dp)
    ) {
        Chip(
            text = "Recent",
            isSelected = false,
            onClick = {}
        )
    }
}

@Composable
@Preview
private fun SelectedChipWithIconPreview() {
    Box(
        modifier = Modifier
            .background(BackgroundColor)
            .padding(10.dp)
    ) {
        Chip(
            text = "Starred",
            painter = painterResource(Res.drawable.star_svg),
            isSelected = true,
            onClick = {}
        )
    }
}

@Composable
@Preview
private fun NotSelectedChipWithIconPreview() {
    Box(
        modifier = Modifier
            .background(BackgroundColor)
            .padding(10.dp)
    ) {
        Chip(
            text = "Starred",
            painter = painterResource(Res.drawable.star_svg),
            isSelected = false,
            onClick = {}
        )
    }
}