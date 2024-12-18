package com.akcay.justwatch.data.remote.model.response.movie.moviemodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition

data class OnBoardingDataWithLottie(
    val lottieFile: Int,
    val title: String,
    val description: String
)

data class OnBoardingDataWithImage(
    val image: String,
    val title: String,
    val description: String
)

@Composable
fun LoaderIntro(modifier: Modifier, file: Int) {
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(file))

    LottieAnimation(
        composition = composition,
        iterations = LottieConstants.IterateForever,
        modifier = modifier
    )
}
