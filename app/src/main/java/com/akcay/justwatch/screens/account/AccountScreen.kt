package com.akcay.justwatch.screens.account

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.akcay.justwatch.R
import com.akcay.justwatch.internal.component.JWLoadingView
import com.akcay.justwatch.internal.component.JWTopAppBar
import com.akcay.justwatch.internal.component.SettingsSwitchItem
import com.akcay.justwatch.internal.navigation.MainDestination
import com.akcay.justwatch.internal.navigation.NavigationScaffold
import com.akcay.justwatch.ui.theme.JustWatchTheme
import com.akcay.justwatch.ui.theme.LightBlue

@Composable
fun AccountScreen(
    viewModel: AccountViewModel = hiltViewModel(),
    isSelected: (MainDestination) -> Boolean,
    navigateToTab: (MainDestination) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    AccountScreenContent(
        uiState = uiState,
        darkThemeCheckedChange = viewModel::darkThemeCheckedChange,
        isSelected = isSelected,
        navigateToTab = navigateToTab,
    )
}

@Composable
fun AccountScreenContent(
    modifier: Modifier = Modifier,
    uiState: AccountScreenUiState,
    darkThemeCheckedChange: (Boolean) -> Unit = {},
    isSelected: (MainDestination) -> Boolean = { false },
    navigateToTab: (MainDestination) -> Unit = {},
) {
    NavigationScaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            JWTopAppBar(
                title = "Account Settings",
            )
        },
        isSelected = isSelected,
        navigateToTab = navigateToTab,
    ) { innerPadding ->
        JWLoadingView(isLoading = uiState.loading) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(color = LightBlue),
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape),
                    painter = painterResource(R.drawable.ic_launcher_background),
                    contentDescription = "avatar",
                    contentScale = ContentScale.Crop,
                )
                Text(modifier = Modifier.padding(top = 15.dp), text = "Hasan Akçay")

                Spacer(modifier = Modifier.height(30.dp))

                SettingsSwitchItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    title = "Dark Theme",
                    desc = "Try new look on dark version",
                    checked = uiState.darkThemeChecked,
                    onCheckedChange = darkThemeCheckedChange,
                )
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun AccountScreenPreview() {
    JustWatchTheme {
        AccountScreenContent(
            uiState = AccountScreenUiState(),
        )
    }
}
