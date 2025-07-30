package com.akcay.justwatch.internal.component

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.akcay.justwatch.R
import com.akcay.justwatch.internal.navigation.BottomNavSections
import com.akcay.justwatch.internal.navigation.MainDestination

@Composable
fun JWBottomNavBar(
    modifier: Modifier = Modifier,
    isSelected: (MainDestination) -> Boolean,
    selectedItem: (MainDestination) -> Unit,
    items: List<BottomNavSections>,
) {
    NavigationBar(modifier = modifier, tonalElevation = 2.dp) {
        items.forEach { justWatchBottomNavBarItems ->
            val isSelected = isSelected(justWatchBottomNavBarItems.route)
            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    if (isSelected) selectedItem.invoke(justWatchBottomNavBarItems.route)
                },
                label = {
                    Text(
                        text = stringResource(id = justWatchBottomNavBarItems.titleResId),
                        fontFamily = FontFamily(
                            Font(R.font.tt_medium),
                        ),
                    )
                },
                icon = {
                    val iconRes = if (isSelected) {
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
