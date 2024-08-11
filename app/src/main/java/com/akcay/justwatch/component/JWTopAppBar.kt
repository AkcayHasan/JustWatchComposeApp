package com.akcay.justwatch.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
    upPress: (() -> Unit)? = null,
    toolbarTitle: String? = null,
    titleColor: Color = Color.Black,
    barScrollBehavior: TopAppBarScrollBehavior,
    isNavigationIconVisible: Boolean = true,
    isActionIconVisible: Boolean = false
) {

    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Transparent),
        scrollBehavior = barScrollBehavior,
        title = {
            Text(
                text = toolbarTitle ?: "", fontFamily = FontFamily(
                    Font(
                        resId = R.font.tt_medium
                    )
                ),
                color = titleColor
            )
        },
        navigationIcon = {
            IconButton(onClick = {
                if (upPress != null) {
                    upPress()
                }
            }) {
                if (isNavigationIconVisible) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Localized description"
                    )
                }
            }
        },
        actions = {
            if (isActionIconVisible) {
                IconButton(onClick = {

                }) {
                    Icon(
                        imageVector = Icons.Filled.FavoriteBorder,
                        contentDescription = "Localized description"
                    )
                }
            }
        }
    )
}