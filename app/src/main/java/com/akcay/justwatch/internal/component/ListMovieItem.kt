package com.akcay.justwatch.internal.component

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.akcay.justwatch.R
import com.akcay.justwatch.internal.util.Constants
import com.akcay.justwatch.ui.theme.JustWatchTheme

@Composable
fun ListMovieItem(
    imageUrl: String,
    itemId: Long,
    movieName: String,
    onCardClicked: (id: Long) -> Unit,
    onAddIconClicked: (id: Long) -> Unit,
) {
    val context = LocalContext.current
    val fullImageUrl = remember(imageUrl) {
        ImageRequest.Builder(context).data("${Constants.BASE_IMAGE_URL}$imageUrl").crossfade(false).build()
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .padding(5.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp)
                .clickable { onCardClicked(itemId) },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape),
                model = fullImageUrl,
                placeholder = null,
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )

            Text(
                modifier = Modifier
                    .padding(start = 10.dp)
                    .weight(1f),
                text = movieName,
                style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.SemiBold),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )

            Icon(
                modifier = Modifier
                    .padding(start = 10.dp)
                    .clickable {
                        onAddIconClicked.invoke(itemId)
                    },
                imageVector = Icons.Default.Add,
                contentDescription = null,
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun ListMovieItemPreviewLight() {
    JustWatchTheme {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp)
                .padding(5.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(50.dp),
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = null,
                )

                Text(
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .weight(1f),
                    text = "Venom: The Last Dance",
                    style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.SemiBold),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )

                Icon(
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .clickable {},
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                )
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ListMovieItemPreviewDark() {
    JustWatchTheme {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp)
                .padding(5.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(50.dp),
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = null,
                )

                Text(
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .weight(1f),
                    text = "Venom: The Last Dance",
                    style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.SemiBold),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )

                Icon(
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .clickable {},
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                )
            }
        }
    }
}
