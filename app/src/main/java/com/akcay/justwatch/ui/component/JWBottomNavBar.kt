package com.akcay.justwatch.ui.component

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.akcay.justwatch.BottomNavSections
import com.akcay.justwatch.R

@Composable
fun JWBottomNavBar(
    modifier: Modifier,
    navController: NavHostController,
    selectedItem: (BottomNavSections) -> Unit,
    items: List<BottomNavSections>
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
                    Text(
                        text = stringResource(id = justWatchBottomNavBarItems.titleResId),
                        fontFamily = FontFamily(
                            Font(R.font.tt_medium)
                        )
                    )
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