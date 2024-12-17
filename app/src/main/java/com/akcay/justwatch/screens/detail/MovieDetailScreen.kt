package com.akcay.justwatch.screens.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.akcay.justwatch.R
import com.akcay.justwatch.internal.component.CastItemView
import com.akcay.justwatch.internal.component.JWLoadingView
import com.akcay.justwatch.internal.component.JWTopAppBar
import com.akcay.justwatch.internal.util.Constants

@Composable
fun MovieDetailScreen(
  viewModel: MovieDetailViewModel = hiltViewModel(),
  upPress: () -> Unit,
  movieId: Long
) {
  val uiState by viewModel.uiState.collectAsState()

  LaunchedEffect(key1 = movieId) {
    with(viewModel) {
      clearState()
      getMovieDetailById(movieId)
      getMovieCastById(movieId)
      getMovieVideoById(movieId)
    }
  }

  MovieDetailScreenContent(
    uiState = uiState,
    upPress = upPress,
    hideLoading = viewModel::hideLoading
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreenContent(
  modifier: Modifier = Modifier,
  upPress: () -> Unit,
  hideLoading: () -> Unit,
  uiState: MovieDetailUiState
) {

  val painter = rememberAsyncImagePainter(
    model = ImageRequest.Builder(LocalContext.current)
      .data("${Constants.BASE_IMAGE_URL}${uiState.movieDetail?.posterPath}")
      .size(Size.ORIGINAL)
      .build()
  ).apply {
    when (state) {
      is AsyncImagePainter.State.Success -> {
        hideLoading.invoke()
      }

      else -> {}
    }
  }

  Scaffold(
    modifier = Modifier.fillMaxSize(),
    topBar = {
      JWTopAppBar(
        barScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
        navigationIcon = {
          IconButton(onClick = {
            upPress.invoke()
          }) {
            Box(
              modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(Color.Gray.copy(alpha = 0.5f)),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                modifier = Modifier.size(20.dp),
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Localized description",
                tint = Color.White
              )
            }
          }
        },
        actionIcons = {
          IconButton(onClick = {

          }) {
            Box(
              modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(Color.Gray.copy(alpha = 0.5f)),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                modifier = Modifier.size(20.dp),
                imageVector = Icons.Filled.FavoriteBorder,
                contentDescription = "Localized description",
                tint = Color.White
              )
            }
          }
        }
      )
    }
  ) {
    JWLoadingView(isLoading = uiState.loadingState) {
      Column(
        modifier = Modifier
          .padding(bottom = it.calculateBottomPadding())
          .fillMaxSize()
          .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
      ) {
        Box(modifier = Modifier.clickable {
          // TODO: exoplayer for trailer
        }, contentAlignment = Alignment.Center) {
          Image(painter = painter, contentDescription = null)
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
          uiState.movieDetail?.genres?.forEach { genre ->
            Text(text = genre.name)
          }
        }
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = uiState.movieDetail?.originalTitle ?: "", fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(5.dp))
        Text(
          modifier = Modifier.padding(horizontal = 5.dp),
          text = uiState.movieDetail?.overview ?: ""
        )

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
            uiState.movieCast?.cast?.let { casts ->
              items(casts.size) { position ->
                CastItemView(
                  imagePath = "${Constants.BASE_IMAGE_URL}${casts[position].profilePath}",
                  castName = casts[position].name
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
@Preview
fun MovieDetailScreenPreview() {

}