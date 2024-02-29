package com.akcay.justwatch.screens.popular

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
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.akcay.justwatch.component.JWTopAppBar
import com.akcay.justwatch.component.ListMovieItem
import com.akcay.justwatch.navigation.Screen
import com.akcay.justwatch.util.Constants

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
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
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
                                imageUrl = "",
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

@Preview
@Composable
fun PopularMoviesScreenPreview() {

}