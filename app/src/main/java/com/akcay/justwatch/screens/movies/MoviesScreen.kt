package com.akcay.justwatch.screens.movies

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.akcay.justwatch.data.remote.model.response.movie.moviemodel.User
import com.akcay.justwatch.internal.component.JWTab
import com.akcay.justwatch.internal.component.JWTopAppBar
import com.akcay.justwatch.internal.component.ListMovieItem
import com.akcay.justwatch.data.remote.model.response.movie.moviemodel.listresponse.MovieResponse
import com.akcay.justwatch.data.remote.model.response.movie.moviemodel.listresponse.MovieResult
import com.akcay.justwatch.internal.util.Constants

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MoviesScreen(
  viewModel: MoviesViewModel = hiltViewModel(),
  onCardClick: (Long) -> Unit
) {
  val uiState by viewModel.uiState.collectAsState()
  val selectedIndex by viewModel.selectedIndex.collectAsState()

  MoviesScreenContent(
    uiState = uiState,
    selectedIndex = selectedIndex,
    onTabSelected = viewModel::setSelectedIndex,
    onCardClick = onCardClick
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesScreenContent(
  uiState: MoviesUiState,
  selectedIndex: Int,
  onTabSelected: (Int) -> Unit,
  onCardClick: (Long) -> Unit
) {
  Scaffold(
    modifier = Modifier.fillMaxSize(),
    topBar = {
      JWTopAppBar(
        upPress = null,
        toolbarTitle = "Movies",
        barScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
        isNavigationIconVisible = false
      )
    }
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(top = it.calculateTopPadding())
    ) {
      Text(
        modifier = Modifier.padding(horizontal = 20.dp),
        text = buildAnnotatedString {
          withStyle(style = SpanStyle(fontSize = 12.sp, fontWeight = FontWeight.Bold)) {
            append("Hi, ")
          }
          withStyle(style = SpanStyle(fontSize = 30.sp)) {
            append(uiState.user?.firstName ?: "")
          }
        }
      )
      JWTab(tabIndex = onTabSelected)
      when (selectedIndex) {
        0 -> {
          LazyColumn {
            uiState.movieList?.movieResults?.let { itemsList ->
              items(count = itemsList.size) { index ->
                val item = itemsList[index]
                ListMovieItem(
                  imageUrl = item.posterPath,
                  itemId = item.id ?: Constants.ZERO,
                  movieName = item.originalTitle ?: "",
                  onCardClicked = { onCardClick(item.id ?: Constants.ZERO) },
                  onAddIconClicked = {}
                )
              }
            }
          }
        }

        1 -> Box { Text(text = "Second Page") }
      }
    }
  }
}

@Preview
@Composable
fun MoviesScreenPreview() {
  MoviesScreenContent(
    uiState = MoviesUiState(
      user = User(firstName = "Preview User"),
      movieList = MovieResponse(
        movieResults = listOf(
          MovieResult(id = 1, originalTitle = "Sample Movie", posterPath = "")
        ),
        page = null,
        totalResults = null,
        totalPages = null
      )
    ),
    selectedIndex = 0,
    onTabSelected = {},
    onCardClick = {}
  )
}