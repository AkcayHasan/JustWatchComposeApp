package com.akcay.justwatch.component

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.akcay.justwatch.screens.home.JustWatchBottomNavBarItems

@Composable
fun JWBottomNavBar(
    modifier: Modifier,
    navController: NavHostController,
    selectedItem: (JustWatchBottomNavBarItems) -> Unit,
    items: List<JustWatchBottomNavBarItems>
) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    NavigationBar(modifier = modifier) {
        items.forEach { justWatchBottomNavBarItems ->
            val selected =
                justWatchBottomNavBarItems.route == backStackEntry.value?.destination?.route
            NavigationBarItem(
                selected = selected,
                onClick = {
                    selectedItem.invoke(justWatchBottomNavBarItems)
                },
                label = {
                    Text(text = stringResource(id = justWatchBottomNavBarItems.titleResId))
                },
                icon = {
                    Icon(
                        imageVector = justWatchBottomNavBarItems.drawableResId,
                        contentDescription = ""
                    )
                }
            )
        }
    }
}