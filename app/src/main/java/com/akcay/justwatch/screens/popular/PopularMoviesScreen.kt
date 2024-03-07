package com.akcay.justwatch.screens.popular

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.akcay.justwatch.component.JWTopAppBar
import com.akcay.justwatch.component.ListMovieItem
import com.akcay.justwatch.data.model.listresponse.MovieResponse
import com.akcay.justwatch.data.model.listresponse.MovieResult
import com.akcay.justwatch.navigation.Screen
import com.akcay.justwatch.util.Constants

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PopularMoviesScreen(
    viewModel: PopularMoviesViewModel = hiltViewModel(),
    navController: NavController
) {

    val list by viewModel.popularMovieList.collectAsState()
    val loadingState by viewModel.loadingState.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            JWTopAppBar(
                navController = navController,
                toolbarTitle = "List",
                barScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
                isNavigationIconVisible = false
            )
        },
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(top = it.calculateTopPadding())
        ) {
            if (loadingState) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn {
                    list?.movieResults?.let { itemsList ->
                        items(count = itemsList.size) { index ->
                            ListMovieItem(
                                imageUrl = itemsList[index].posterPath,
                                itemId = itemsList[index].id ?: Constants.ZERO,
                                movieName = itemsList[index].originalTitle ?: "",
                                onCardClicked = {
                                    navController.navigate(Screen.MovieDetail.createRoute(itemsList[index].id ?: Constants.ZERO))
                                },
                                onAddIconClicked = {}
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun PopularMoviesScreenPreview() {
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
        topBar = { CenterAlignedTopAppBar(title = { Text(text = "List") }) }
    ) {
        Column(
            modifier = Modifier
                .padding(top = it.calculateTopPadding(), start = 10.dp, end = 10.dp, bottom = 10.dp)
                .fillMaxSize()
        ) {
            LazyColumn {
                list.movieResults?.let { itemsList ->
                    items(itemsList.size) { index ->
                        ListMovieItem(
                            imageUrl = "",
                            itemId = itemsList[index].id ?: 0,
                            movieName = itemsList[index].originalTitle ?: "",
                            onCardClicked = {},
                            onAddIconClicked = {}
                        )
                    }
                }
            }
        }
    }
}