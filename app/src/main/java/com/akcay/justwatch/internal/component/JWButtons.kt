package com.akcay.justwatch.internal.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun JWButton(
    modifier: Modifier = Modifier,
    text: String? = null,
    enabled: Boolean = true,
    textColor: Color = Color.White,
    backgroundColor: Color,
    onClick: () -> Unit,
    icon: ImageVector? = null,
    iconColor: Color? = null,
) {
    Button(
        modifier = modifier, onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
        ),
        enabled = enabled,
    ) {
        val endPadding = if (text != null) 6.dp else 0.dp
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            icon?.let {
                Icon(
                    modifier = Modifier.padding(end = endPadding),
                    imageVector = it,
                    contentDescription = null,
                    tint = iconColor ?: Color.Unspecified
                )
            }
            text?.let {
                Text(
                    modifier = Modifier.width(IntrinsicSize.Max),
                    text = it,
                    maxLines = 1,
                    style = TextStyle(color = textColor),
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Clip,
                )
            }
        }
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
            backgroundColor = Color.White,
            onClick = {}
        )
        JWButton(
            modifier = Modifier.width(200.dp),
            text = "Next",
            textColor = Color.Gray,
            backgroundColor = Color.LightGray,
            onClick = {}
        )
    }
}
