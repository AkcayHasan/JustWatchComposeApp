package com.akcay.justwatch.screens.login

import android.annotation.SuppressLint
import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.akcay.justwatch.R
import com.akcay.justwatch.ui.theme.Green2
import com.akcay.justwatch.ui.theme.LightBlue
import com.akcay.justwatch.ui.theme.LightGray

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginScreen() {
    Scaffold() {

    }
}

@Preview
@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun LoginScreenPreview() {
    Scaffold(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 100.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .width(150.dp)
                    .height(150.dp),
                painter = painterResource(id = R.drawable.just_watch_logo),
                contentDescription = "JustWatchLogo"
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 50.dp, start = 20.dp),
                fontFamily = FontFamily(
                    Font(
                        R.font.tt_bold
                    )
                ), color = LightBlue,
                fontSize = 25.sp,
                text = "Create Your Account",
                textAlign = TextAlign.Start
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp, start = 30.dp),
                fontFamily = FontFamily(
                    Font(
                        R.font.tt_bold
                    )
                ),
                text = "Email",
                textAlign = TextAlign.Start,
                fontSize = 15.sp
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, start = 20.dp, end = 20.dp),
                shape = RoundedCornerShape(10.dp),
                value = "",
                onValueChange = {},
                leadingIcon = { Icon(Icons.Default.Email, "", tint = Gray) },
                maxLines = 1
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp, start = 30.dp),
                fontFamily = FontFamily(
                    Font(
                        R.font.tt_bold
                    )
                ),
                text = "Password",
                textAlign = TextAlign.Start,
                fontSize = 15.sp
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, start = 20.dp, end = 20.dp),
                shape = RoundedCornerShape(10.dp),
                value = "",
                onValueChange = {},
                trailingIcon = { Icon(Icons.Default.Search, "", tint = Gray) },
                leadingIcon = { Icon(Icons.Default.Lock, "", tint = Gray) },
                maxLines = 1
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, end = 30.dp),
                fontFamily = FontFamily(
                    Font(
                        R.font.tt_bold
                    )
                ),
                text = "Forgot password?",
                color = Blue,
                textAlign = TextAlign.End,
                fontSize = 15.sp
            )
            ElevatedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(top = 50.dp, start = 30.dp, end = 30.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonColors(
                    containerColor = Green2,
                    contentColor = White,
                    disabledContentColor = Green,
                    disabledContainerColor = Green
                ), onClick = { /*TODO*/ }) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    fontFamily = FontFamily(
                        Font(
                            R.font.tt_bold
                        )
                    ),
                    text = "Log In",
                    color = White,
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp
                )
            }

            Row(modifier = Modifier.padding(top = 20.dp)) {
                Text(
                    text = "Don't have an account?", fontFamily = FontFamily(
                        Font(
                            R.font.tt_medium
                        )
                    )
                )
                Text(
                    modifier = Modifier
                        .padding(start = 5.dp)
                        .clickable {

                        }, fontFamily = FontFamily(
                        Font(
                            R.font.tt_bold
                        )
                    ), text = "Sign Up"
                )
            }

            Text(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .clickable {

                    }, fontFamily = FontFamily(
                    Font(
                        R.font.tt_bold
                    )
                ), text = "Entry As Guest"
            )
        }
    }
}