package com.akcay.justwatch.screens.movies

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.akcay.justwatch.R
import com.akcay.justwatch.internal.component.JWTopAppBar
import com.akcay.justwatch.internal.component.ListMovieItem
import com.akcay.justwatch.data.remote.model.response.movie.moviemodel.listresponse.MovieResult
import com.akcay.justwatch.internal.component.JWLoadingView
import com.akcay.justwatch.internal.component.JWTabRow
import com.akcay.justwatch.internal.component.JWTabTitle
import com.akcay.justwatch.internal.util.Constants

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MoviesScreen(
  viewModel: MoviesViewModel = hiltViewModel(),
  onCardClick: (Long) -> Unit
) {
  val uiState by viewModel.uiState.collectAsState()
  val movies = viewModel.popularMovies.collectAsLazyPagingItems()
  val listState = rememberLazyListState()
  var selectedTabPosition by remember { mutableIntStateOf(0) }

  MoviesScreenContent(
    uiState = uiState,
    movieList = movies,
    selectedIndex = selectedTabPosition,
    listState = listState,
    tabItems = listOf(
      "Popular", "Upcoming"
    ),
    onTabSelected = {
      selectedTabPosition = it
    },
    onCardClick = onCardClick
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesScreenContent(
  uiState: MoviesUiState,
  movieList: LazyPagingItems<MovieResult>,
  selectedIndex: Int,
  listState: LazyListState,
  tabItems: List<String>,
  onTabSelected: (Int) -> Unit,
  onCardClick: (Long) -> Unit
) {
  Scaffold(
    modifier = Modifier.fillMaxSize(),
    topBar = {
      JWTopAppBar(
        titleIcon = {
          Icon(
            modifier = Modifier.padding(10.dp),
            painter = painterResource(R.drawable.just_watch_logo),
            contentDescription = null,
            tint = Color.Red
          )
        },
        navigationIcon = {
          IconButton(onClick = {

          }) {
            Icon(
              modifier = Modifier.size(20.dp),
              painter = painterResource(R.drawable.ic_nav_driver),
              contentDescription = null,
              tint = Color.Black
            )
          }
        },
        actionIcons = {
          IconButton(onClick = {

          }) {
            Icon(
              modifier = Modifier.size(20.dp),
              painter = painterResource(R.drawable.ic_bell),
              contentDescription = null,
              tint = Color.Black
            )
          }
        },
        barScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
      )
    }
  ) {
    JWLoadingView(isLoading = uiState.loading) {
      Column(
        modifier = Modifier
          .fillMaxSize()
          .padding(top = it.calculateTopPadding() + 10.dp)
      ) {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, bottom = 10.dp),
          contentAlignment = Alignment.Center
        ) {
          JWTabRow(selectedTabPosition = selectedIndex) {
            tabItems.forEachIndexed { index, name ->
              JWTabTitle(
                modifier = Modifier.padding(horizontal = 40.dp, vertical = 10.dp),
                title = name,
                position = index
              ) {
                onTabSelected.invoke(index)
              }
            }
          }
        }
        when (selectedIndex) {
          0 -> {
            LazyColumn(state = listState) {
              items(movieList.itemCount) { index ->
                val item = movieList[index]
                ListMovieItem(
                  imageUrl = item?.posterPath,
                  itemId = item?.id ?: Constants.ZERO,
                  movieName = item?.originalTitle ?: "",
                  onCardClicked = { onCardClick(item?.id ?: Constants.ZERO) },
                  onAddIconClicked = {}
                )
              }
            }
          }

          1 -> Box { Text(text = "Second Page") }
        }
      }
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun MoviesScreenPreview() {
  Scaffold(
    modifier = Modifier.fillMaxSize(),
    topBar = {
      JWTopAppBar(
        titleIcon = {
          Icon(
            modifier = Modifier.padding(10.dp),
            painter = painterResource(R.drawable.just_watch_logo),
            contentDescription = null,
            tint = Color.Red
          )
        },
        navigationIcon = {
          IconButton(onClick = {

          }) {
            Icon(
              modifier = Modifier.size(20.dp),
              painter = painterResource(R.drawable.ic_nav_driver),
              contentDescription = null,
              tint = Color.Black
            )
          }
        },
        actionIcons = {
          IconButton(onClick = {

          }) {
            Icon(
              modifier = Modifier.size(20.dp),
              painter = painterResource(R.drawable.ic_bell),
              contentDescription = null,
              tint = Color.Black
            )
          }
        },
        barScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
      )
    }
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(top = it.calculateTopPadding() + 10.dp)
    ) {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 10.dp),
        contentAlignment = Alignment.Center
      ) {
        JWTabRow(selectedTabPosition = 0) {
          listOf("popular", "upcoming").forEachIndexed { index, name ->
            JWTabTitle(
              modifier = Modifier.padding(horizontal = 40.dp, vertical = 10.dp),
              title = name,
              position = index
            ) {

            }
          }
        }
      }
      LazyColumn(modifier = Modifier.align(Alignment.CenterHorizontally)) {
        items(3){ index ->
          Text("asdasdasdasdasd")
          Text("asdasdasdasdasd")
          Text("asdasdasdasdasd")
          Text("asdasdasdasdasd")
        }
      }
    }
  }
}