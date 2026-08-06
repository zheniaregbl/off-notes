package com.nimain.home.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nimain.core.presentation.theme.BackgroundColor

@Composable
internal fun Chip(
    modifier: Modifier = Modifier,
    text: String,
    quantity: Int = 0,
    painter: Painter? = null,
    isSelected: Boolean = false,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .clip(CircleShape)
            .background(if (isSelected) Color(0xFFEFD999) else Color(0xFF212010))
            .clickable(
                enabled = true,
                onClick = onClick
            )
            .padding(vertical = 14.dp, horizontal = 22.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        painter?.let {
            Icon(
                modifier = Modifier.size(17.dp),
                painter = painter,
                contentDescription = null,
                tint = if (isSelected) Color(0xFF212010) else Color(0xFFEFD999)
            )
            Spacer(Modifier.width(6.dp))
        }
        Text(
            text = text,
            style = TextStyle(
                color = if (isSelected) BackgroundColor else Color(0xFFE3E3E3),
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = quantity.toString(),
            style = TextStyle(
                color = if (isSelected) BackgroundColor else Color(0xFFE3E3E3),
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )
        )
    }
}