package com.akcay.justwatch.screens.profile

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.akcay.justwatch.ui.component.JWTopAppBar
import com.akcay.justwatch.ui.component.SettingsSwitchItem
import com.akcay.justwatch.ui.theme.LightBlue
import com.akcay.justwatch.ui.theme.LightGray

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProfileScreen() {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = LightGray,
        topBar = {
            JWTopAppBar(
                upPress = null,
                toolbarTitle = "Account Settings",
                titleColor = Color.White,
                barScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
                isNavigationIconVisible = false
            )
        },
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .background(color = LightBlue)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProfileScreenPreview() {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = LightGray,
        topBar = {
            JWTopAppBar(
                upPress = null,
                toolbarTitle = "Account Settings",
                titleColor = Color.White,
                barScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
                isNavigationIconVisible = false
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .background(color = LightBlue)
        )
        Column(modifier = Modifier.padding(innerPadding)) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .shadow(
                        elevation = 10.dp,
                        shape = RoundedCornerShape(8.dp)
                    ),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(1.dp)
            ) {
                LazyColumn {
                    items(4) {
                        Text(text = "asdasdasd")
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .shadow(
                        elevation = 10.dp,
                        shape = RoundedCornerShape(8.dp)
                    ),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(1.dp)
            ) {
                LazyColumn {
                    items(4) { index ->
                        Text(
                            modifier = Modifier.padding(top = 5.dp, bottom = 5.dp, start = 10.dp),
                            text = "asdasdasd"
                        )
                        if (index < 3)
                            HorizontalDivider(
                                modifier = Modifier.padding(horizontal = 10.dp),
                                thickness = 1.dp,
                                color = Color.LightGray
                            )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .shadow(
                        elevation = 10.dp,
                        shape = RoundedCornerShape(8.dp)
                    ),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                LazyColumn {
                    items(4) { index ->
                        SettingsSwitchItem("","")
                        if (index < 3)
                            HorizontalDivider(
                                modifier = Modifier.padding(horizontal = 10.dp),
                                thickness = 1.dp,
                                color = Color.LightGray
                            )
                    }
                }
            }
        }
    }
}