package com.nimain.home.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import offlinenote.feature.home.generated.resources.Res
import offlinenote.feature.home.generated.resources.search_svg
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun SearchBar(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    hint: String = "",
    onImeSearch: () -> Unit = { }
) {
    CompositionLocalProvider(
        LocalTextSelectionColors provides
                TextSelectionColors(
                    handleColor = Color(0xFFEFD999),
                    backgroundColor = Color(0xFFEFD999).copy(.4f)
                )
    ) {
        BasicTextField(
            modifier = modifier,
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            textStyle = TextStyle(
                color = Color(0xFFE3E3E3),
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            ),
            cursorBrush = SolidColor(Color(0xFFEFD999))
        ) { innerTextField ->
            Row(
                modifier = Modifier
                    .clip(CircleShape)
                    .fillMaxWidth()
                    .background(Color(0xFF212010))
                    .padding(vertical = 12.dp, horizontal = 22.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    innerTextField()
                    if (value.isBlank()) {
                        Text(
                            text = hint,
                            style = TextStyle(
                                color = Color(0xFFE3E3E3).copy(.4f),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Normal
                            )
                        )
                    }
                }
                Image(
                    modifier = Modifier.size(30.dp),
                    painter = painterResource(Res.drawable.search_svg),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(Color(0xFFEFD999))
                )
            }
        }
    }
}