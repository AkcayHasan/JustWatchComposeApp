package com.akcay.justwatch.internal.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun JWTab(tabIndex: (Int) -> Unit) {
    var selectedIndex by remember { mutableIntStateOf(0) }

    val list = listOf("Popular", "Upcoming")

    TabRow(
        selectedTabIndex = selectedIndex,
        containerColor = Color(0xff1E76DA),
        modifier = Modifier
            .padding(10.dp)
            .clip(RoundedCornerShape(50)),
        indicator = {
            Box{}
        },
        divider = {}
    ) {
        tabIndex.invoke(selectedIndex)
        list.forEachIndexed { index, text ->
            val selected = selectedIndex == index
            Tab(
                modifier = if (selected) {
                    Modifier
                        .padding(2.dp)
                        .clip(RoundedCornerShape(50))
                        .background(
                            Color.White
                        )
                } else {
                    Modifier
                        .padding(2.dp)
                        .clip(RoundedCornerShape(50))
                        .background(
                            Color(0xff1E76DA)
                        )
                },
                selected = selected,
                onClick = { selectedIndex = index },
                text = { Text(text = text, color = Color(0xff6FAAEE)) }
            )
        }
    }
}

@Composable
@Preview
fun JWTabPreview() {
    var selectedIndex by remember { mutableIntStateOf(0) }

    val list = listOf("Popular", "Upcoming")

    TabRow(
        selectedTabIndex = selectedIndex,
        containerColor = Color(0xff1E76DA),
        modifier = Modifier
            .padding(10.dp)
            .clip(RoundedCornerShape(50)),
        indicator = {
            Box{}
        },
        divider = {}
    ) {
        list.forEachIndexed { index, text ->
            val selected = selectedIndex == index
            Tab(
                modifier = if (selected) {
                    Modifier
                        .padding(2.dp)
                        .clip(RoundedCornerShape(50))
                        .background(
                            Color.White
                        )
                } else {
                    Modifier
                        .padding(2.dp)
                        .clip(RoundedCornerShape(50))
                        .background(
                            Color(0xff1E76DA)
                        )
                },
                selected = selected,
                onClick = { selectedIndex = index },
                text = { Text(text = text, color = Color(0xff6FAAEE)) }
            )
        }
    }
}