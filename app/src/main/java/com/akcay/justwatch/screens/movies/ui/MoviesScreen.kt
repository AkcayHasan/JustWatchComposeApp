package com.akcay.justwatch.screens.movies.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.akcay.justwatch.internal.component.JWLoadingView
import com.akcay.justwatch.internal.component.JWTabRow
import com.akcay.justwatch.internal.component.JWTopAppBar
import com.akcay.justwatch.internal.component.ListMovieItem
import com.akcay.justwatch.internal.component.TabRowItem
import com.akcay.justwatch.internal.navigation.MainDestination
import com.akcay.justwatch.internal.navigation.NavigationScaffold
import com.akcay.justwatch.ui.theme.JustWatchTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MoviesScreen(
    viewModel: MoviesViewModel = hiltViewModel(),
    onCardClick: (Long) -> Unit,
    isSelected: (MainDestination) -> Boolean,
    navigateToTab: (MainDestination) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    MoviesScreenContent(
        uiState = uiState,
        onCardClick = onCardClick,
        loadMore = viewModel::loadMore,
        onAddIconClick = viewModel::onAddIconClicked,
        isSelected = isSelected,
        navigateToTab = navigateToTab,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesScreenContent(
    uiState: MoviesUiState,
    onCardClick: (Long) -> Unit = {},
    onAddIconClick: (Long) -> Unit = {},
    loadMore: () -> Unit = {},
    isSelected: (MainDestination) -> Boolean = { false },
    navigateToTab: (MainDestination) -> Unit = {},
    onTabChange: (TabRowItem) -> Unit = {},
) {
    val listState = rememberLazyListState()
    val isOnBottom by remember {
        derivedStateOf {
            with(listState.layoutInfo) {
                visibleItemsInfo.lastOrNull()?.index == totalItemsCount - 1
            }
        }
    }

    LaunchedEffect(isOnBottom) {
        if (isOnBottom) loadMore()
    }

    NavigationScaffold(
        isSelected = isSelected,
        navigateToTab = navigateToTab,
        topBar = {
            JWTopAppBar(
              title = "Movies",
            )
        },
        content = {
            JWLoadingView(isLoading = uiState.loading) {
                Column(
                  modifier = Modifier
                    .fillMaxSize()
                    .padding(top = it.calculateTopPadding() + 10.dp),
                  horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    JWTabRow(
                        items = listOf(TabRowItem.ACTIVE, TabRowItem.UPCOMING),
                        onTabChange = onTabChange,
                    )
                    LazyColumn(state = listState) {
                        items(items = uiState.movieList) { item ->
                            ListMovieItem(
                              imageUrl = item.image,
                              itemId = item.id,
                              movieName = item.title,
                              onCardClicked = onCardClick,
                              onAddIconClicked = onAddIconClick,
                            )
                        }
                    }
                }
            }
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun MoviesScreenPreview() {
    JustWatchTheme {
        MoviesScreenContent(
          uiState = MoviesUiState(),
        )
    }
}
