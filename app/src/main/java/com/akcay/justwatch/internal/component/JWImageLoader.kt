package com.akcay.justwatch.internal.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.akcay.justwatch.internal.util.Constants

@Composable
fun JWImageLoader(imageUrl: String?) {
  val painter = rememberAsyncImagePainter(
    model = "${Constants.BASE_IMAGE_URL}$imageUrl"
  )

  Box(modifier = Modifier.size(50.dp)) {
    when (painter.state) {
      is AsyncImagePainter.State.Loading -> {
        CircularProgressIndicator(
          modifier = Modifier.align(Alignment.Center)
        )
      }
      is AsyncImagePainter.State.Success -> {
        Image(
          painter = painter,
          contentDescription = null,
          modifier = Modifier
            .clip(CircleShape)
            .size(50.dp),
          contentScale = ContentScale.Crop
        )
      }
      else -> {
        // Hata durumları veya diğer durumlar
      }
    }
  }
}