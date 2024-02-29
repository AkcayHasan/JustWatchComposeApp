package com.akcay.justwatch.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JWTopAppBar(navController: NavController, toolbarTitle: String, barScrollBehavior: TopAppBarScrollBehavior, isNavigationIconVisible: Boolean = true) {

    CenterAlignedTopAppBar(
        scrollBehavior = barScrollBehavior,
        title = {
            Text(text = toolbarTitle)
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
        }
    )
}