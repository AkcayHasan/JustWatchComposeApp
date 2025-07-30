package com.akcay.justwatch.screens.favourite

import android.annotation.SuppressLint
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.akcay.justwatch.R
import com.akcay.justwatch.internal.component.JWLoadingView
import com.akcay.justwatch.internal.component.JWTopAppBar
import com.akcay.justwatch.internal.component.SettingsSwitchItem
import com.akcay.justwatch.ui.theme.JustWatchTheme
import com.akcay.justwatch.ui.theme.LightBlue
import com.akcay.justwatch.ui.theme.LightGray

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FavouriteMoviesScreen(
    viewModel: FavouriteMoviesViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    ProfileScreenContent(
        uiState = uiState,
        darkThemeCheckedChange = viewModel::darkThemeCheckedChange,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreenContent(
    modifier: Modifier = Modifier,
    uiState: FavouriteMoviesUiState,
    darkThemeCheckedChange: (Boolean) -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = LightGray,
        topBar = {
            JWTopAppBar(
                title = "Account Settings",
            )
        },
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

@OptIn(ExperimentalMaterial3Api::class)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProfileScreenPreview() {
    JustWatchTheme {
        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            containerColor = LightGray,
            topBar = {
                JWTopAppBar(
                    title = "Account Settings",
                )
            },
        ) { innerPadding ->
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
                Text(
                    modifier = Modifier.padding(top = 15.dp),
                    text = "Hasan Akçay",
                    style = TextStyle(color = MaterialTheme.colorScheme.onSurface),
                )

                Spacer(modifier = Modifier.height(30.dp))

                SettingsSwitchItem(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    title = "Dark Theme",
                    desc = "Try new look on dark version",
                    checked = true,
                    onCheckedChange = {},
                )
            }
        }
    }
}

/*
LazyColumn {
            items(items.size) { index ->
              SettingsSwitchItem(title = items[index].title, desc = items[index].desc)
              if (index < items.size.minus(1))
                HorizontalDivider(
                  modifier = Modifier.padding(horizontal = 10.dp),
                  thickness = 1.dp,
                  color = MaterialTheme.colorScheme.surface
                )
            }
          }
 */

@OptIn(ExperimentalMaterial3Api::class)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProfileScreenPreviewDark() {
    JustWatchTheme {
        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            containerColor = LightGray,
            topBar = {
                JWTopAppBar(
                    title = "Account Settings",
                )
            },
        ) { innerPadding ->
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
                Text(
                    modifier = Modifier.padding(top = 15.dp),
                    text = "Hasan Akçay",
                    style = TextStyle(color = MaterialTheme.colorScheme.onSurface),
                )

                Spacer(modifier = Modifier.height(30.dp))

                SettingsSwitchItem(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    title = "Dark Theme",
                    desc = "Try new look on dark version",
                    checked = true,
                    onCheckedChange = {},
                )
            }
        }
    }
}
