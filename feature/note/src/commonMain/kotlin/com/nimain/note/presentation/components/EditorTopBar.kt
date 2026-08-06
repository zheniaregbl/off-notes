package com.nimain.note.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.nimain.core.presentation.theme.BackgroundColor
import offlinenote.feature.note.generated.resources.Res
import offlinenote.feature.note.generated.resources.check_svg
import offlinenote.feature.note.generated.resources.plus_svg
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun EditorTopBar(
    modifier: Modifier = Modifier,
    onCancel: () -> Unit,
    onConfirm: () -> Unit
) {
    Box(modifier = modifier) {
        Row(
            modifier = Modifier.align(Alignment.CenterEnd),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            TopBarActionButton(
                modifier = Modifier.rotate(45f),
                painter = painterResource(Res.drawable.plus_svg),
                containerColor = Color(0xFFD84443),
                contentColor = BackgroundColor,
                onClick = onCancel
            )
            TopBarActionButton(
                painter = painterResource(Res.drawable.check_svg),
                containerColor = Color.White,
                contentColor = BackgroundColor,
                onClick = onConfirm
            )
        }
    }
}

@Composable
internal fun TopBarActionButton(
    modifier: Modifier = Modifier,
    painter: Painter,
    containerColor: Color,
    contentColor: Color,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .size(44.dp)
            .clip(CircleShape)
            .background(containerColor)
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            painter = painter,
            contentDescription = null,
            tint = contentColor
        )
    }
}