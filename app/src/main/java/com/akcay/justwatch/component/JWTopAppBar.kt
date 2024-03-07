package com.akcay.justwatch.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JWTopAppBar(
    navController: NavController,
    toolbarTitle: String? = null,
    barScrollBehavior: TopAppBarScrollBehavior,
    isNavigationIconVisible: Boolean = true,
    isActionIconVisible: Boolean = false
) {

    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Transparent),
        scrollBehavior = barScrollBehavior,
        title = {
            Text(text = toolbarTitle ?: "")
        },
        navigationIcon = {
            IconButton(onClick = {
                navController.navigateUp()
            }) {
                if (isNavigationIconVisible) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
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