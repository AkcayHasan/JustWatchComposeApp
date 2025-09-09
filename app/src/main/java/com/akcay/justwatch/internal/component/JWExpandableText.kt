package com.akcay.justwatch.internal.component

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.akcay.justwatch.ui.theme.JustWatchTheme

@Composable
fun JWExpandableText(
    initialText: String,
    modifier: Modifier = Modifier,
    maxLine: Int = 6,
) {
    var isExpanded by remember { mutableStateOf(false) }
    var showButton by remember { mutableStateOf(false) }
    Column(
        modifier = modifier,
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize(),
            maxLines = if (isExpanded) Int.MAX_VALUE else maxLine,
            overflow = TextOverflow.Ellipsis,
            text = initialText,
            style = JustWatchTheme.typography.body,
            onTextLayout = {
                if (!isExpanded) {
                    showButton = it.lineCount >= maxLine && it.isLineEllipsized(maxLine - 1)
                }
            },
        )
        if (showButton) {
            Row(
                modifier = Modifier
                    .clickable(
                        indication = null,
                        interactionSource = null,
                        onClick = {
                            isExpanded = !isExpanded
                        },
                    )
                    .padding(top = 8.dp),
                content = {
                    val text = if (isExpanded) "Less" else "More"
                    Text(
                        text = text,
                        style = JustWatchTheme.typography.body,
                        color = JustWatchTheme.colors.onPrimaryContainer,
                    )
                    Crossfade(
                        targetState = isExpanded,
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .align(Alignment.CenterVertically),
                    ) {
                        val icon = if (it) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown
                        Icon(
                            modifier = Modifier.size(18.dp),
                            imageVector = icon,
                            contentDescription = null,
                            tint = JustWatchTheme.colors.onPrimaryContainer,
                        )
                    }
                },
            )
        }
    }
}
