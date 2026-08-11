package com.nimain.home.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nimain.core.presentation.theme.BackgroundColor
import com.nimain.home.presentation.model.NoteUiModel

@Composable
internal fun NoteItem(
    modifier: Modifier = Modifier,
    noteUiModel: NoteUiModel,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(
                RoundedCornerShape(
                    topStart = 8.dp,
                    topEnd = 22.dp,
                    bottomStart = 22.dp,
                    bottomEnd = 50.dp
                )
            )
            .background(Color(0xFFF28788))
            .clickable(
                enabled = true,
                onClick = onClick
            )
            .padding(vertical = 16.dp, horizontal = 22.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Text(
                text = noteUiModel.title,
                style = TextStyle(
                    color = BackgroundColor,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = noteUiModel.preview,
                style = TextStyle(
                    color = BackgroundColor,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal
                ),
                maxLines = 4,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}