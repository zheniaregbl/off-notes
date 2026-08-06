package com.nimain.home.preview.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nimain.core.presentation.theme.BackgroundColor
import com.nimain.home.presentation.components.AddButton

@Composable
@Preview
private fun AddButtonPreview() {
    Box(
        modifier = Modifier
            .background(BackgroundColor)
            .padding(10.dp)
    ) {
        AddButton(onClick = {})
    }
}