package com.akcay.justwatch.internal.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.akcay.justwatch.ui.theme.JustWatchTheme

@Composable
fun CastItemView(
    imagePath: String,
    castName: String,
    knownForDepartment: String,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ){
        Card(
            modifier = Modifier.size(40.dp),
            shape = RoundedCornerShape(20.dp),
        ) {
            AsyncImage(
                model = imagePath,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }
        Column {
            Text(
                text = castName,
                style = JustWatchTheme.typography.body.copy(color = Color.Black)
            )

            Text(
                text = knownForDepartment,
                style = JustWatchTheme.typography.label.copy(color = JustWatchTheme.colors.secondary)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CastItemViewPreview() {
    JustWatchTheme {
        CastItemView(
            imagePath = "",
            castName = "John Doe",
            knownForDepartment = "Acting"
        )
    }
}
