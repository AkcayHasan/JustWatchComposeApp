package com.akcay.justwatch.internal.navigation

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.akcay.justwatch.R
import com.akcay.justwatch.internal.component.JWBottomNavBar

@Composable
fun NavigationScaffold(
    modifier: Modifier = Modifier,
    isSelected: (MainDestination) -> Boolean,
    navigateToTab: (MainDestination) -> Unit,
    topBar: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = topBar,
        bottomBar = {
            JWBottomNavBar(
                isSelected = isSelected,
                selectedItem = navigateToTab,
                items = listOf(
                    BottomNavSections.MOVIES,
                    BottomNavSections.FAVOURITE,
                    BottomNavSections.SETTINGS
                )
            )
        }
    ) {
        content(it)
    }
}

enum class BottomNavSections(
    @param: StringRes val titleResId: Int,
    val selectedDrawableResId: Int,
    val notSelectedDrawableResId: Int,
    val route: MainDestination,
) {
    MOVIES(
        titleResId = R.string.movies_title,
        selectedDrawableResId = R.drawable.ic_home_solid,
        notSelectedDrawableResId = R.drawable.ic_home,
        route = MainDestination.Movies,
    ),
    SETTINGS(
        titleResId = R.string.settings_title,
        selectedDrawableResId = R.drawable.ic_search_solid,
        notSelectedDrawableResId = R.drawable.ic_search,
        route = MainDestination.Account,
    ),
    FAVOURITE(
        titleResId = R.string.favourite_title,
        selectedDrawableResId = R.drawable.ic_favourite_solid,
        notSelectedDrawableResId = R.drawable.ic_favourite,
        route = MainDestination.Favourite,
    )
}
