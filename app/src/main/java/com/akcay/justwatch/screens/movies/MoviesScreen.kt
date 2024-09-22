package com.akcay.justwatch.screens.movies

import android.annotation.SuppressLint
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.akcay.justwatch.ui.component.JWTab
import com.akcay.justwatch.ui.component.JWTopAppBar
import com.akcay.justwatch.ui.component.ListMovieItem
import com.akcay.justwatch.data.remote.model.response.movie.moviemodel.listresponse.MovieResponse
import com.akcay.justwatch.data.remote.model.response.movie.moviemodel.listresponse.MovieResult
import com.akcay.justwatch.internal.util.Constants

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesScreen(
    viewModel: MoviesViewModel = hiltViewModel(),
    onCardClick: (Long) -> Unit
) {

    var selectedIndex by remember { mutableIntStateOf(0) }
    val list by viewModel.popularMovieList.collectAsState()
    val loadingState by viewModel.loadingState.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.getAllPopularMovies()
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
            if (loadingState) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                JWTab(tabIndex = { index ->
                    selectedIndex = index
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
}

@Composable
fun MoviesScreenContent() {

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
            JWTab(tabIndex = { index ->

            })
            LazyColumn {
                list.movieResults?.let { itemsList ->
                    items(count = itemsList.size) { index ->
                        ListMovieItem(
                            imageUrl = itemsList[index].posterPath,
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