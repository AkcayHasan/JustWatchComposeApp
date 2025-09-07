package com.akcay.justwatch.internal.component

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.akcay.justwatch.R
import com.akcay.justwatch.internal.navigation.BottomNavSections
import com.akcay.justwatch.internal.navigation.MainDestination
import com.akcay.justwatch.ui.theme.JustWatchTheme

@Composable
fun JWBottomNavBar(
    modifier: Modifier = Modifier,
    isSelected: (MainDestination) -> Boolean,
    selectedItem: (MainDestination) -> Unit,
    items: List<BottomNavSections>,
) {
    NavigationBar(
        modifier = modifier, 
        tonalElevation = 2.dp,
        containerColor = JustWatchTheme.colors.surface
    ) {
        items.forEach { justWatchBottomNavBarItems ->
            val isItemSelected = isSelected(justWatchBottomNavBarItems.route)
            NavigationBarItem(
                selected = isItemSelected,
                onClick = {
                    selectedItem.invoke(justWatchBottomNavBarItems.route)
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = JustWatchTheme.colors.primary,
                    selectedTextColor = JustWatchTheme.colors.primary,
                    unselectedIconColor = JustWatchTheme.colors.onSurface,
                    unselectedTextColor = JustWatchTheme.colors.onSurface,
                    indicatorColor = JustWatchTheme.colors.primaryContainer
                ),
                label = {
                    Text(
                        text = stringResource(id = justWatchBottomNavBarItems.titleResId),
                        fontFamily = FontFamily(
                            Font(R.font.tt_medium),
                        ),
                    )
                },
                icon = {
                    val iconRes = if (isItemSelected) {
                        justWatchBottomNavBarItems.selectedDrawableResId
                    } else {
                        justWatchBottomNavBarItems.notSelectedDrawableResId
                    }
                    Icon(
                        modifier = Modifier.size(18.dp),
                        painter = painterResource(iconRes),
                        contentDescription = "",
                    )
                },
            )
        }
    }
}
