package com.nimain.home.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dokar.sheets.m3.BottomSheet
import com.dokar.sheets.rememberBottomSheetState
import com.nimain.core.extension.defaultScreen
import com.nimain.core.presentation.theme.BackgroundColor
import com.nimain.home.presentation.components.AddButton
import com.nimain.home.presentation.components.NoteActionsContent
import com.nimain.home.presentation.components.NoteItem
import com.nimain.home.presentation.components.SearchBar
import com.nimain.home.presentation.components.TagSection
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun HomeScreen(onNoteClick: (String?) -> Unit) {
    val viewModel: HomeViewModel = koinViewModel()
    val state = viewModel.state.collectAsStateWithLifecycle()
    val searchQuery = viewModel.searchQuery.collectAsStateWithLifecycle()

    HomeScreenContent(
        modifier = Modifier.defaultScreen(),
        state = state,
        searchQuery = searchQuery,
        onAction = viewModel::onAction,
        onNoteClick = onNoteClick
    )
}

@Composable
internal fun HomeScreenContent(
    modifier: Modifier = Modifier,
    state: State<HomeScreenState>,
    searchQuery: State<String>,
    onAction: (HomeAction) -> Unit = {},
    onNoteClick: (String?) -> Unit
) {
    var selectedNoteId by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberBottomSheetState()

    fun showNoteActions() = scope.launch { bottomSheetState.expand() }
    fun hideNoteActions() = scope.launch { bottomSheetState.collapse() }

    Box(modifier = modifier) {
        Column(modifier = Modifier.fillMaxSize()) {
            SearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 10.dp),
                value = searchQuery.value,
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
            state.value.DisplayResult(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 14.dp),
                onLoading = { CircularProgressIndicator(color = Color(0xFFEFD999)) },
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
                        LazyVerticalStaggeredGrid(
                            modifier = Modifier.fillMaxSize(),
                            columns = StaggeredGridCells.Fixed(2),
                            verticalItemSpacing = 10.dp,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            items(success.notes, key = { it.id }) { note ->
                                NoteItem(
                                    modifier = Modifier.fillMaxWidth(),
                                    noteUiModel = note,
                                    onClick = { onNoteClick(note.id) },
                                    onLongClick = {
                                        selectedNoteId = note.id
                                        showNoteActions()
                                    }
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
        BottomSheet(
            modifier = Modifier,
            state = bottomSheetState,
            skipPeeked = true,
            backgroundColor = BackgroundColor
        ) {
            NoteActionsContent(
                modifier = Modifier.fillMaxWidth(),
                selectedNoteId = selectedNoteId,
                onAction = onAction,
                onDismissRequest = { hideNoteActions() }
            )
        }
    }
}
