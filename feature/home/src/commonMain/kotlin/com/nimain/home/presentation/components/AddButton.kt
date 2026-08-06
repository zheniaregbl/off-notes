package com.nimain.home.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.nimain.core.presentation.theme.BackgroundColor
import offlinenote.feature.home.generated.resources.Res
import offlinenote.feature.home.generated.resources.plus_svg
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun AddButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(Color(0xFFEFD999))
            .clickable(
                enabled = true,
                onClick = onClick
            )
            .padding(18.dp)
    ) {
        Icon(
            modifier = Modifier.size(26.dp),
            painter = painterResource(Res.drawable.plus_svg),
            contentDescription = null,
            tint = BackgroundColor
        )
    }
}