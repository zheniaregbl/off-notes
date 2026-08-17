package com.nimain.note.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.nimain.core.presentation.theme.BackgroundColor

@Composable
internal fun TitleInputField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit
) {
    CompositionLocalProvider(
        LocalTextSelectionColors provides TextSelectionColors(
            handleColor = Color.White,
            backgroundColor = Color.White.copy(.4f)
        )
    ) {
        BasicTextField(
            modifier = modifier,
            value = value,
            onValueChange = onValueChange,
            singleLine = false,
            textStyle = TextStyle(
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            ),
            cursorBrush = SolidColor(Color.White)
        ) { innerTextField ->
            Box(modifier = Modifier.fillMaxWidth()) { innerTextField() }
        }
    }
}

@Composable
internal fun ContentInputField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    hint: String = ""
) {
    val focusRequester = remember { FocusRequester() }

    CompositionLocalProvider(
        LocalTextSelectionColors provides TextSelectionColors(
            handleColor = Color.White,
            backgroundColor = Color.White.copy(.4f)
        )
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { focusRequester.requestFocus() }
        ) {
            BasicTextField(
                modifier = Modifier
                    .fillMaxSize()
                    .focusRequester(focusRequester),
                value = value,
                onValueChange = onValueChange,
                singleLine = false,
                minLines = 10,
                textStyle = TextStyle(
                    color = BackgroundColor,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal
                ),
                cursorBrush = SolidColor(Color.White)
            ) { innerTextField ->
                Box(modifier = Modifier.fillMaxSize()) {
                    if (value.isBlank()) {
                        Text(
                            text = hint,
                            style = TextStyle(
                                color = BackgroundColor.copy(.4f),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Normal
                            )
                        )
                    }
                    innerTextField()
                }
            }
        }
    }
}