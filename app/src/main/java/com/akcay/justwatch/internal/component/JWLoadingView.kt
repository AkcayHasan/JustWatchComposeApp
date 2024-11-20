package com.akcay.justwatch.internal.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun JWLoadingView(
  modifier: Modifier = Modifier,
  isLoading: Boolean,
  block: @Composable () -> Unit
) {
  if (isLoading) {
    Box(
      modifier = modifier
        .fillMaxSize()
        .background(Color.White)
        .clickable(enabled = false) { },
      contentAlignment = Alignment.Center
    ) {
      CircularProgressIndicator(
        modifier = modifier.width(50.dp),
        strokeWidth = 4.dp,
        color = Color.Green
      )
    }
  } else {
    block.invoke()
  }
}