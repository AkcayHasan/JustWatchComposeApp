package com.akcay.justwatch.screens.detail

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.akcay.justwatch.R
import com.akcay.justwatch.internal.component.CastItemView
import com.akcay.justwatch.internal.component.JWTopAppBar
import com.akcay.justwatch.internal.util.Constants

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreen(
    viewModel: MovieDetailViewModel = hiltViewModel(),
    upPress: () -> Unit,
    movieId: Long
) {

    LaunchedEffect(key1 = Unit) {
        with(viewModel) {
            getMovieDetailById(movieId)
            getMovieCastById(movieId)
            getMovieVideoById(movieId)
        }
    }

    val movieDetail by viewModel.movieById.collectAsState()
    val castList by viewModel.castById.collectAsState()
    val loadingState by viewModel.loadingState.collectAsState()
    //val videoList by viewModel.videoById.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            JWTopAppBar(
                upPress = upPress,
                barScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
                isNavigationIconVisible = true,
                isActionIconVisible = true
            )
        }
    ) {
        if (loadingState) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box(modifier = Modifier.clickable {
                    // TODO: exoplayer for trailer
                }, contentAlignment = Alignment.Center) {
                    AsyncImage(
                        model = "${Constants.BASE_IMAGE_URL}${movieDetail?.posterPath}",
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth
                    )
                    Image(
                        modifier = Modifier
                            .size(50.dp)
                            .zIndex(1f),
                        painter = painterResource(id = R.drawable.play),
                        contentDescription = "play button"
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    movieDetail?.genres?.forEach {
                        Text(text = it.name)
                    }
                }
                Spacer(modifier = Modifier.height(5.dp))
                Text(text = movieDetail?.originalTitle ?: "", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(5.dp))
                Text(modifier = Modifier.padding(horizontal = 5.dp), text = movieDetail?.overview ?: "")

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    text = "Cast:",
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Bold,
                )

                LazyRow(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    content = {
                        castList?.cast?.let { casts ->
                            items(casts.size) {
                                CastItemView(
                                    imagePath = "${Constants.BASE_IMAGE_URL}${casts[it].profilePath}",
                                    castName = casts[it].name
                                )
                            }
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun MovieDetailScreenPreview() {

}