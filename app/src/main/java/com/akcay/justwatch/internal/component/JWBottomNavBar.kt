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
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.akcay.justwatch.R
import com.akcay.justwatch.screens.home.BottomNavSections

@Composable
fun JWBottomNavBar(
  modifier: Modifier,
  navController: NavHostController,
  selectedItem: (BottomNavSections) -> Unit,
  items: List<BottomNavSections>
) {
  val backStackEntry = navController.currentBackStackEntryAsState()
  NavigationBar(modifier = modifier, tonalElevation = 2.dp) {
    items.forEach { justWatchBottomNavBarItems ->
      val selected =
        justWatchBottomNavBarItems.route == backStackEntry.value?.destination?.route
      NavigationBarItem(
        selected = selected,
        onClick = {
          if (!selected) {
            selectedItem.invoke(justWatchBottomNavBarItems)
          }
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
          val iconRes = if (selected) {
            justWatchBottomNavBarItems.selectedDrawableResId
          } else {
            justWatchBottomNavBarItems.notSelectedDrawableResId
          }
          Icon(
            modifier = Modifier.size(18.dp),
            painter = painterResource(iconRes),
            contentDescription = ""
          )
        }
      )
    }
  }
}