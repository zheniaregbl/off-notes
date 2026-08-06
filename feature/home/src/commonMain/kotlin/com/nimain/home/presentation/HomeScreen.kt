package com.nimain.home.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nimain.core.extension.defaultScreen
import com.nimain.home.presentation.components.AddButton
import com.nimain.home.presentation.components.SearchBar
import com.nimain.home.presentation.components.TagSection

@Composable
internal fun HomeScreen(onNoteClick: (String?) -> Unit) {
    HomeScreenContent(
        modifier = Modifier.defaultScreen(),
        onNoteClick = onNoteClick
    )
}

@Composable
internal fun HomeScreenContent(
    modifier: Modifier = Modifier,
    onNoteClick: (String?) -> Unit
) {
    var searchText by remember { mutableStateOf("") }

    Box(modifier = modifier) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item {
                SearchBar(
                    modifier = Modifier.fillMaxWidth(),
                    value = searchText,
                    hint = "Input text...",
                    onValueChange = { searchText = it }
                )
            }
            item {
                TagSection(
                    modifier = Modifier.fillMaxWidth(),
                    horizontal = Arrangement.spacedBy(8.dp),
                    vertical = Arrangement.spacedBy(8.dp)
                )
            }
            item {

            }
        }
        AddButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .navigationBarsPadding()
                .padding(bottom = 40.dp, end = 40.dp),
            onClick = { onNoteClick(null) }
        )
    }
}

