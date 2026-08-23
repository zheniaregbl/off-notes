package com.nimain.home.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp

@Composable
internal actual fun TopScreenSection(
    modifier: Modifier,
    searchQuery: State<String>,
    onSearchQueryChange: (String) -> Unit,
    onAddNote: () -> Unit
) {
    SearchBarWithMatchedHeightButton(
        modifier = Modifier
            .widthIn(max = 560.dp)
            .then(modifier),
        searchBar = {
            SearchBar(
                modifier = Modifier.width(400.dp),
                value = searchQuery.value,
                hint = "Input text...",
                onValueChange = onSearchQueryChange
            )
        },
        button = {
            AddButton(onClick = onAddNote)
        }
    )
}

@Composable
private fun SearchBarWithMatchedHeightButton(
    modifier: Modifier = Modifier,
    searchBar: @Composable () -> Unit,
    button: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = {
            Box { searchBar() }
            Box { button() }
        }
    ) { measurables, constraints ->
        val (searchBarMeasurable, buttonMeasurable) = measurables
        val searchBarPlaceable = searchBarMeasurable.measure(
            constraints.copy(minWidth = 0)
        )
        val buttonPlaceable = buttonMeasurable.measure(
            Constraints.fixed(
                width = searchBarPlaceable.height,
                height = searchBarPlaceable.height
            )
        )
        val spacing = 10.dp.roundToPx()
        val totalWidth = searchBarPlaceable.width + spacing + buttonPlaceable.width

        layout(totalWidth, searchBarPlaceable.height) {
            searchBarPlaceable.placeRelative(0, 0)
            buttonPlaceable.placeRelative(
                x = searchBarPlaceable.width + spacing,
                y = 0
            )
        }
    }
}