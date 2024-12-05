package com.akcay.justwatch.internal.component

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.akcay.justwatch.ui.theme.Green2

@SuppressLint("UseOfNonLambdaOffsetOverload")
@Composable
fun JWSwitchButton(
  checked: Boolean,
  onCheckedChange: (Boolean) -> Unit,
  modifier: Modifier = Modifier,
  checkedColor: Color = Green2,
  uncheckedColor: Color = Color(0xFFD1D1D6),
  thumbColor: Color = Color.White
) {
  var isChecked by remember { mutableStateOf(checked) }

  val thumbOffset by animateDpAsState(targetValue = if (isChecked) 16.dp else 1.dp, label = "")

  LaunchedEffect(checked) {
    isChecked = checked
  }

  Box(
    modifier = modifier
      .width(36.dp)
      .height(21.dp)
      .clip(CircleShape)
      .background(if (isChecked) checkedColor else uncheckedColor)
      .clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        isChecked = !isChecked
        onCheckedChange(isChecked)
      },
    contentAlignment = Alignment.CenterStart
  ) {
    Box(
      modifier = Modifier
        .offset(x = thumbOffset)
        .size(19.dp)
        .clip(CircleShape)
        .background(thumbColor)
    )
  }
}

@Preview(showBackground = true)
@Composable
fun CustomIosSwitchPreview() {
  var isChecked by remember { mutableStateOf(true) }

  JWSwitchButton(
    checked = isChecked,
    onCheckedChange = { isChecked = it },
    modifier = Modifier.padding(10.dp)
  )
}