package com.akcay.justwatch.internal.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun JWButton(
  modifier: Modifier = Modifier,
  text: String,
  enabled: Boolean = true,
  textColor: Color,
  backgroundColor: Color,
  onClick: () -> Unit
) {
  Button(
    modifier = modifier, onClick = onClick, colors = ButtonDefaults.buttonColors(
      containerColor = backgroundColor
    ),
    enabled = enabled
  ) {
    Text(
      modifier = Modifier.width(IntrinsicSize.Max),
      text = text,
      maxLines = 1,
      style = TextStyle(color = textColor),
      fontWeight = FontWeight.Bold,
      overflow = TextOverflow.Clip
    )
  }
}

@Preview
@Composable
fun BasicButtonPreview() {
  Column {
    JWButton(
      modifier = Modifier.width(200.dp),
      text = "Next",
      textColor = Color.Black,
      backgroundColor = Color.White
    ) { }
    JWButton(
      modifier = Modifier.width(200.dp),
      text = "Next",
      textColor = Color.Gray,
      backgroundColor = Color.LightGray
    ) { }
  }
}
