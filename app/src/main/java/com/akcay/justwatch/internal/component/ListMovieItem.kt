package com.akcay.justwatch.internal.component

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.akcay.justwatch.internal.util.Constants
import com.akcay.justwatch.ui.theme.JustWatchTheme
import java.util.Locale

data class ListMovieItemModel(
    val imageUrl: String,
    val itemId: Long,
    val movieName: String,
    val voteAverage: Double,
)

@Composable
fun ListMovieItem(
    model: ListMovieItemModel,
    onCardClicked: (id: Long, text: String) -> Unit,
) {
    val context = LocalContext.current
    val fullImageUrl = remember(model.imageUrl) {
        ImageRequest.Builder(context).data("${Constants.BASE_IMAGE_URL}${model.imageUrl}").crossfade(false).build()
    }
    Card(
        modifier = Modifier
            .width(160.dp)
            .clickable {
                onCardClicked(model.itemId, model.movieName)
            },
        shape = RoundedCornerShape(16.dp),
    ) {
        Column {
            Box {
                AsyncImage(
                    model = fullImageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(2f / 3f),
                )
                RatingChip(
                    text = String.format(Locale.US, "%.1f", model.voteAverage),
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp),
                )
            }

            Text(
                text = model.movieName,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = JustWatchTheme.typography.body,
                modifier = Modifier
                    .padding(start = 12.dp, top = 8.dp, end = 12.dp),
            )
            Text(
                text = model.movieName,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = JustWatchTheme.typography.label,
                modifier = Modifier.padding(start = 12.dp, bottom = 12.dp, end = 12.dp),
            )
        }
    }
}

@Composable
fun RatingChip(text: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .background(color = Color.Black.copy(alpha = 0.5f), shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier
                .padding(end = 4.dp)
                .size(16.dp),
            imageVector = Icons.Outlined.Star,
            contentDescription = null,
            tint = JustWatchTheme.colors.primaryContainer,
        )
        Text(text = text, color = JustWatchTheme.colors.primaryContainer, style = JustWatchTheme.typography.label)
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun ListMovieItemPreviewLight() {
    JustWatchTheme {
        ListMovieItem(
            model = ListMovieItemModel(
                imageUrl = "",
                itemId = 0L,
                movieName = "Venom: The Last Dance",
                voteAverage = 4.3,
            ),
            onCardClicked = {_, _ -> },
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ListMovieItemPreviewDark() {
    JustWatchTheme {
        ListMovieItem(
            model = ListMovieItemModel(
                imageUrl = "",
                itemId = 0L,
                movieName = "Venom: The Last Dance",
                voteAverage = 4.3,
            ),
            onCardClicked = {_, _ -> },
        )
    }
}
