package com.akcay.justwatch.screens.login

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.akcay.justwatch.MainDestinations
import com.akcay.justwatch.R
import com.akcay.justwatch.component.EmailField
import com.akcay.justwatch.R.string as AppText
import com.akcay.justwatch.R.drawable as AppIcon
import com.akcay.justwatch.component.JWRoundedCheckBox
import com.akcay.justwatch.component.PasswordField
import com.akcay.justwatch.ext.isValidEmail
import com.akcay.justwatch.navigation.Screen
import com.akcay.justwatch.ui.theme.Green2
import com.akcay.justwatch.ui.theme.LightBlue

@Composable
fun LoginScreen(
    navigate: (String) -> Unit,
    navigateAndPopUp: (String, String) -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState

    LoginScreenContent(
        uiState = uiState,
        onForgotPasswordClick = { navigate.invoke("") },
        onEntryAsGuestClick = { viewModel.createAnonymousUser(navigateAndPopUp) },
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onLoginClick = { viewModel.onLoginClick(navigateAndPopUp) },
        onSignInClick = { navigate.invoke(Screen.Register.route) }
    )
}

@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun LoginScreenContent(
    uiState: LoginUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    onSignInClick: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    onEntryAsGuestClick: () -> Unit
) {
    var checked by remember { mutableStateOf(false) }

    var passwordVisible by remember { mutableStateOf(false) }

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
                painter = painterResource(id = AppIcon.just_watch_logo),
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
                text = "Welcome Back!",
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
            EmailField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, start = 20.dp, end = 20.dp),
                shape = RoundedCornerShape(10.dp),
                value = uiState.email,
                onNewValue = onEmailChange
            )
            JWRoundedCheckBox(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
                label = "We can use animations to make it behave similar to the default",
                isChecked = uiState.email.isValidEmail()
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
            PasswordField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, start = 20.dp, end = 20.dp),
                value = uiState.password,
                shape = RoundedCornerShape(10.dp),
                onNewValue = onPasswordChange,
                isVisible = passwordVisible,
                visibilityClick = { passwordVisible = !passwordVisible }
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
                ), onClick = onLoginClick
            ) {
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
                            onSignInClick.invoke()
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
                        onEntryAsGuestClick.invoke()
                    }, fontFamily = FontFamily(
                    Font(
                        R.font.tt_bold
                    )
                ), text = "Entry As Guest"
            )
        }
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
                text = "Welcome Back!",
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
            EmailField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, start = 20.dp, end = 20.dp),
                shape = RoundedCornerShape(10.dp),
                value = "Email",
                onNewValue = {}
            )
            JWRoundedCheckBox(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
                label = "We can use animations to make it behave similar to the default",
                isChecked = true
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
            PasswordField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, start = 20.dp, end = 20.dp),
                value = "asdasd",
                shape = RoundedCornerShape(10.dp),
                onNewValue = {},
                isVisible = false,
                visibilityClick = {}
            )
            Row {
                JWRoundedCheckBox(
                    modifier = Modifier.padding(top = 10.dp, start = 20.dp),
                    label = "Remember Me",
                    isChecked = false,
                    isClickable = true
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
            }
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