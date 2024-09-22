package com.akcay.justwatch.screens.register

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.akcay.justwatch.R.string as AppText
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.akcay.justwatch.R
import com.akcay.justwatch.ui.component.EmailField
import com.akcay.justwatch.ui.component.JWRoundedCheckBox
import com.akcay.justwatch.ui.component.PasswordField
import com.akcay.justwatch.internal.ext.isValidEmail
import com.akcay.justwatch.internal.ext.passwordMatches
import com.akcay.justwatch.navigation.Screen
import com.akcay.justwatch.ui.theme.Green2
import com.akcay.justwatch.ui.theme.LightBlue

@Composable
fun RegisterScreen(
    navigateAndPopUp: (String, String) -> Unit,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState
    val loadingState by viewModel.loadingStatus.collectAsStateWithLifecycle()

    RegisterScreenContent(
        uiState = uiState,
        loadingState = loadingState,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onRepeatPasswordChange = viewModel::onRepeatPasswordChange,
        onRegisterClick = { viewModel.onRegisterClick(navigateAndPopUp) },
        onLoginClick = { navigateAndPopUp.invoke(Screen.Login.route, Screen.Register.route) }
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RegisterScreenContent(
    uiState: RegisterUiState,
    loadingState: Boolean,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onRepeatPasswordChange: (String) -> Unit,
    onRegisterClick: () -> Unit,
    onLoginClick: () -> Unit
) {
    var isPasswordVisibility by remember {
        mutableStateOf(false)
    }

    var isRepeatPasswordVisibility by remember {
        mutableStateOf(false)
    }

    val passwordMatches =
        uiState.password.isNotEmpty() && uiState.repeatPassword.passwordMatches(uiState.password)


    Scaffold(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (loadingState) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
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
                        .padding(top = 20.dp),
                    fontFamily = FontFamily(
                        Font(
                            R.font.tt_bold
                        )
                    ), color = LightBlue,
                    fontSize = 25.sp,
                    text = "Create your account",
                    textAlign = TextAlign.Center
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
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, start = 30.dp),
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
                    isVisible = isPasswordVisibility,
                    visibilityClick = { isPasswordVisibility = !isPasswordVisibility }
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, start = 30.dp),
                    fontFamily = FontFamily(
                        Font(
                            R.font.tt_bold
                        )
                    ),
                    text = "Confirm Password",
                    textAlign = TextAlign.Start,
                    fontSize = 15.sp
                )
                PasswordField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, start = 20.dp, end = 20.dp),
                    value = uiState.repeatPassword,
                    shape = RoundedCornerShape(10.dp),
                    onNewValue = onRepeatPasswordChange,
                    isVisible = isRepeatPasswordVisibility,
                    visibilityClick = { isRepeatPasswordVisibility = !isRepeatPasswordVisibility }
                )
                JWRoundedCheckBox(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 10.dp),
                    label = "Please make sure the passwords match!",
                    isChecked = passwordMatches
                )
                ElevatedButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .padding(top = 50.dp, start = 30.dp, end = 30.dp),
                    shape = RoundedCornerShape(10.dp),
                    enabled = passwordMatches,
                    colors = ButtonColors(
                        containerColor = Green2,
                        contentColor = Color.White,
                        disabledContentColor = Color.White,
                        disabledContainerColor = Color.Gray
                    ), onClick = {
                        onRegisterClick.invoke()
                    }) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        fontFamily = FontFamily(
                            Font(
                                R.font.tt_bold
                            )
                        ),
                        text = stringResource(id = AppText.button_register),
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp
                    )
                }

                Row(modifier = Modifier.padding(top = 20.dp)) {
                    Text(
                        text = "Already have an account?", fontFamily = FontFamily(
                            Font(
                                R.font.tt_medium
                            )
                        )
                    )
                    Text(
                        modifier = Modifier
                            .padding(start = 5.dp)
                            .clickable {
                                onLoginClick.invoke()
                            }, fontFamily = FontFamily(
                            Font(
                                R.font.tt_bold
                            )
                        ), text = "Login"
                    )
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
@Preview
fun RegisterScreenPreview() {
    Scaffold(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 50.dp),
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
                    .padding(top = 20.dp),
                fontFamily = FontFamily(
                    Font(
                        R.font.tt_bold
                    )
                ), color = LightBlue,
                fontSize = 25.sp,
                text = "Create your account",
                textAlign = TextAlign.Center
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
                onNewValue = {

                }
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, start = 30.dp),
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
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, start = 30.dp),
                fontFamily = FontFamily(
                    Font(
                        R.font.tt_bold
                    )
                ),
                text = "Confirm Password",
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
                isVisible = true,
                visibilityClick = {}
            )
            JWRoundedCheckBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 10.dp),
                label = "Please make sure the passwords match!",
                isChecked = true
            )
            ElevatedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(top = 50.dp, start = 30.dp, end = 30.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonColors(
                    containerColor = Green2,
                    contentColor = Color.White,
                    disabledContentColor = Color.Green,
                    disabledContainerColor = Color.Green
                ), onClick = { }) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    fontFamily = FontFamily(
                        Font(
                            R.font.tt_bold
                        )
                    ),
                    text = "Register",
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp
                )
            }

            Row(modifier = Modifier.padding(top = 20.dp)) {
                Text(
                    text = "Already have an account?", fontFamily = FontFamily(
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
                    ), text = "Login"
                )
            }
        }
    }
}