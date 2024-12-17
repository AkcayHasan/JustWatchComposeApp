package com.akcay.justwatch.internal.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.akcay.justwatch.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JWTopAppBar(
    navigationIcon: (@Composable (() -> Unit))? = null,
    actionIcons: (@Composable (RowScope.() -> Unit))? = null,
    toolbarTitle: String = "",
    titleColor: Color = MaterialTheme.colorScheme.onSurface,
    barScrollBehavior: TopAppBarScrollBehavior,
    titleIcon: (@Composable (() -> Unit))? = null
) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Transparent),
        scrollBehavior = barScrollBehavior,
        title = {
            if (titleIcon != null) {
                titleIcon()
            } else if (toolbarTitle.isNotEmpty()) {
                Text(
                    text = toolbarTitle,
                    fontFamily = FontFamily(Font(resId = R.font.tt_medium)),
                    color = titleColor
                )
            }
        },
        navigationIcon = {
            navigationIcon?.invoke()
        },
        actions = {
            actionIcons?.invoke(this)
        }
    )
}