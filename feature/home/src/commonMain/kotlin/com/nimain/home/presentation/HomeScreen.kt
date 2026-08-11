package com.nimain.home.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nimain.core.extension.defaultScreen
import com.nimain.home.presentation.components.AddButton
import com.nimain.home.presentation.components.NoteItem
import com.nimain.home.presentation.components.SearchBar
import com.nimain.home.presentation.components.TagSection
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun HomeScreen(onNoteClick: (String?) -> Unit) {
    val viewModel: HomeViewModel = koinViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    HomeScreenContent(
        modifier = Modifier.defaultScreen(),
        state = state,
        onAction = viewModel::onAction,
        onNoteClick = onNoteClick
    )
}

@Composable
internal fun HomeScreenContent(
    modifier: Modifier = Modifier,
    state: HomeScreenState = HomeScreenState.Loading,
    onAction: (HomeAction) -> Unit = {},
    onNoteClick: (String?) -> Unit
) {
    val searchQuery = (state as? HomeScreenState.Success)?.searchQuery.orEmpty()

    Box(modifier = modifier) {
        Column(modifier = Modifier.fillMaxSize()) {
            SearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp),
                value = searchQuery,
                hint = "Input text...",
                onValueChange = { onAction(HomeAction.OnSearchChange(it)) }
            )
            TagSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 10.dp),
                horizontal = Arrangement.spacedBy(8.dp),
                vertical = Arrangement.spacedBy(8.dp)
            )
            state.DisplayResult(
                modifier = Modifier.fillMaxSize(),
                onLoading = {
                    CircularProgressIndicator(color = Color(0xFFEFD999))
                },
                onEmpty = {
                    Text(
                        text = "No notes yet — tap + to create one",
                        color = Color.White.copy(alpha = .5f)
                    )
                },
                onError = { error ->
                    Text(
                        text = error.message,
                        color = Color(0xFFF28788)
                    )
                },
                onSuccess = { success ->
                    if (success.notes.isEmpty()) {
                        Text(
                            text = "Nothing found for \"${success.searchQuery}\"",
                            color = Color.White.copy(alpha = .5f)
                        )
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(horizontal = 14.dp, vertical = 10.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            items(success.notes, key = { it.id }) { note ->
                                NoteItem(
                                    modifier = Modifier.fillMaxWidth(),
                                    noteUiModel = note,
                                    onClick = { onNoteClick(note.id) }
                                )
                            }
                        }
                    }
                }
            )
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
