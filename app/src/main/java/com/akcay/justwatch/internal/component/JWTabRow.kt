package com.akcay.justwatch.internal.component

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.akcay.justwatch.ui.theme.JustWatchTheme
import kotlin.collections.indexOf

enum class TabRowItem(val text: String) {
    ACTIVE("Active"),
    UPCOMING("Upcoming")
}

@Composable
fun JWTabRow(
    modifier: Modifier = Modifier,
    items: List<TabRowItem>,
    onTabChange: (TabRowItem) -> Unit,
) {
    var selectedItem by rememberSaveable(inputs = items.toTypedArray()) { mutableStateOf(items.first()) }

    val offsetAnimation by animateDpAsState(
        targetValue = items.indexOf(selectedItem) * 124.dp,
        animationSpec = tween(durationMillis = 400),
        label = "",
    )

    Box(
        modifier = modifier
            .height(40.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.tertiary)
            .border(width = 1.dp, color = MaterialTheme.colorScheme.outline, shape = CircleShape)
            .padding(4.dp),
        contentAlignment = Alignment.CenterStart,
    ) {
        Box(
            modifier = Modifier
                .offset(x = offsetAnimation)
                .height(32.dp)
                .width(116.dp)
                .clip(CircleShape)
                .background(Color.Gray),
        )

        Row(
            modifier = Modifier.wrapContentSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items.forEach { item ->
                Box(
                    modifier = Modifier
                        .height(32.dp)
                        .clip(CircleShape)
                        .clickable {
                            selectedItem = item
                            onTabChange(item)
                        },
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        modifier = Modifier.width(116.dp),
                        text = item.text,
                        style = TextStyle(),
                        textAlign = TextAlign.Center,
                        color = if (selectedItem == item) Color.Green else Color.LightGray,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun JWTabRowPreview(
    modifier: Modifier = Modifier,
) {
    JustWatchTheme {
        JWTabRow(
            items = listOf(TabRowItem.ACTIVE, TabRowItem.UPCOMING),
            onTabChange = {  }
        )
    }
}
