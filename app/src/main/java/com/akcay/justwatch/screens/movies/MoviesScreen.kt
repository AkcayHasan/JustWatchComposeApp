package com.akcay.justwatch.screens.movies

import android.annotation.SuppressLint
import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import com.akcay.justwatch.JustWatchNavController
import com.akcay.justwatch.internal.component.JWTab
import com.akcay.justwatch.internal.component.JWTopAppBar
import com.akcay.justwatch.internal.component.ListMovieItem
import com.akcay.justwatch.data.remote.model.response.movie.moviemodel.listresponse.MovieResponse
import com.akcay.justwatch.data.remote.model.response.movie.moviemodel.listresponse.MovieResult
import com.akcay.justwatch.internal.component.JWDialogBox
import com.akcay.justwatch.internal.component.JWDialogBoxModel
import com.akcay.justwatch.internal.component.JWLoadingView
import com.akcay.justwatch.internal.util.Constants

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MoviesScreen(
  justWatchNavController: JustWatchNavController,
  viewModel: MoviesViewModel = hiltViewModel(),
  onCardClick: (Long) -> Unit
) {
  val uiState by viewModel.uiState.collectAsState(initial = MoviesUiState())

  LaunchedEffect(key1 = Unit) {
    viewModel.getAllPopularMovies()
  }

  var selectedIndex by remember { mutableIntStateOf(0) }
  val list by viewModel.popularMovieList.collectAsState()

  MoviesScreenContent(
    justWatchNavController = justWatchNavController,
    uiState = uiState,
    list = list,
    selectedIndex = selectedIndex,
    setSelectedIndex = {
      selectedIndex = it
    },
    onCardClick = onCardClick
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesScreenContent(
  justWatchNavController: JustWatchNavController,
  uiState: MoviesUiState,
  list: MovieResponse?,
  selectedIndex: Int,
  setSelectedIndex: (Int) -> Unit,
  onCardClick: (Long) -> Unit
) {
  val isBackStackEmpty = justWatchNavController.navController.previousBackStackEntry == null
  var showExitDialog by remember {
    mutableStateOf(false)
  }

  val context = LocalContext.current
  val activity = context as? Activity

  BackHandler(enabled = isBackStackEmpty) {
    showExitDialog = true
  }

  Scaffold(
    modifier = Modifier.fillMaxSize(),
    topBar = {
      JWTopAppBar(
        upPress = null,
        toolbarTitle = "Movies",
        barScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
        isNavigationIconVisible = false
      )
    },
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(top = it.calculateTopPadding())
    ) {
      if (uiState.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
          CircularProgressIndicator()
        }
      } else {
        Text(
          modifier = Modifier.padding(horizontal = 20.dp),
          text = buildAnnotatedString {
            withStyle(style = SpanStyle(fontSize = 12.sp, fontWeight = FontWeight.Bold)) {
              append("Hi, ")
            }
            withStyle(style = SpanStyle(fontSize = 30.sp)) {
              append(uiState.currentUser?.email)
            }
          }
        )
        JWTab(tabIndex = { index ->
          setSelectedIndex.invoke(index)
        })
        when (selectedIndex) {
          0 -> {
            LazyColumn {
              list?.movieResults?.let { itemsList ->
                items(count = itemsList.size) { index ->
                  ListMovieItem(
                    imageUrl = itemsList[index].posterPath,
                    itemId = itemsList[index].id ?: Constants.ZERO,
                    movieName = itemsList[index].originalTitle ?: "",
                    onCardClicked = {
                      onCardClick.invoke(
                        itemsList[index].id ?: Constants.ZERO
                      )
                    },
                    onAddIconClicked = {}
                  )
                }
              }
            }
          }

          1 -> {
            Box {
              Text(text = "Second Page")
            }
          }
        }
      }
    }
  }

  if (showExitDialog) {
    JWDialogBox(
      onDismissRequest = { },
      content = JWDialogBoxModel(
        title = "Çıkış",
        description = "Çıkmak mı istiyorsun?",
        positiveButtonText = "Çık",
        negativeButtonText = "Vazgeç"
      ),
      positiveButtonClickAction = {
        activity?.finish()
      },
      negativeButtonClickAction = {
        showExitDialog = false
      }
    )
  }

}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun MoviesScreenPreview() {
  val list = MovieResponse(
    page = null,
    movieResults = arrayListOf(
      MovieResult(),
      MovieResult(),
      MovieResult(),
    ),
    totalPages = null,
    totalResults = null
  )
  Scaffold(
    modifier = Modifier.fillMaxSize(),
    topBar = {
      JWTopAppBar(
        upPress = null,
        toolbarTitle = "Movies",
        barScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
        isNavigationIconVisible = false
      )
    },
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(top = it.calculateTopPadding())
    ) {
      Text(modifier = Modifier.padding(horizontal = 20.dp), text = "Hi, Hasan")
      JWTab(tabIndex = { index ->

      })
      LazyColumn {
        list.movieResults?.let { itemsList ->
          items(count = itemsList.size) { index ->
            ListMovieItem(
              imageUrl = "",
              itemId = itemsList[index].id ?: Constants.ZERO,
              movieName = itemsList[index].originalTitle ?: "",
              onCardClicked = {

              },
              onAddIconClicked = {}
            )
          }
        }
      }
    }
  }
}