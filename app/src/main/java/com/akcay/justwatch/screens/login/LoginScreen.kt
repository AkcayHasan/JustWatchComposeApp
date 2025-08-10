package com.akcay.justwatch.screens.login

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.akcay.justwatch.R
import com.akcay.justwatch.internal.component.JWButton
import com.akcay.justwatch.internal.component.JWDialogBox
import com.akcay.justwatch.internal.component.JWDialogBoxModel
import com.akcay.justwatch.internal.component.JWLoadingView
import com.akcay.justwatch.internal.component.JWPasswordField
import com.akcay.justwatch.internal.component.JWSwitchButton
import com.akcay.justwatch.internal.component.JWTextField
import com.akcay.justwatch.ui.theme.JustWatchTheme

@Composable
fun LoginScreen(
    navigateForgotPassword: () -> Unit,
    navigateMovies: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.channel.collect { event ->
            when (event) {
                LoginScreenViewModelEvent.NavigateMoviesScreen -> navigateMovies()
            }
        }
    }

    LoginScreenContent(
        uiState = uiState,
        onForgotPasswordClick = navigateForgotPassword,
        onEntryAsGuestClick = { navigateMovies() },
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onRememberMeCheckboxClick = viewModel::setRememberMeChecked,
        onLoginClick = { viewModel.sendEvent(LoginScreenViewEvent.OnLoginClicked) },
        onSignUpClick = { viewModel.sendEvent(LoginScreenViewEvent.OnRegisterClicked) },
    )

    if (showDialog) {
        JWDialogBox(
            onDismissRequest = { showDialog = false },
            content = JWDialogBoxModel(
                mainColor = Color.Gray,
                title = "",
                description = "",
                positiveButtonText = "Ok",
            ),
            positiveButtonClickAction = { showDialog = false },
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LoginScreenContent(
    uiState: LoginUiState,
    onEmailChange: (String) -> Unit = {},
    onPasswordChange: (String) -> Unit = {},
    onRememberMeCheckboxClick: (Boolean) -> Unit = {},
    onLoginClick: () -> Unit = {},
    onSignUpClick: () -> Unit = {},
    onForgotPasswordClick: () -> Unit = {},
    onEntryAsGuestClick: () -> Unit = {},
) {
    val scrollState = rememberScrollState()
    val isImeVisible = WindowInsets.isImeVisible

    JWLoadingView(isLoading = uiState.loading) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(JustWatchTheme.colors.background)
                .then(
                    if (isImeVisible) Modifier.verticalScroll(scrollState)
                    else Modifier,
                )
                .padding(top = 100.dp)
                .imePadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                fontFamily = FontFamily(
                    Font(
                        R.font.tt_medium,
                    ),
                ),
                color = Black,
                fontSize = 32.sp,
                text = "Sign In",
                textAlign = TextAlign.Center,
            )
            JWTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 10.dp),
                value = uiState.email,
                label = "E-mail",
                onNewValue = onEmailChange,
            )
            JWPasswordField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, start = 10.dp, end = 10.dp),
                value = uiState.password,
                label = "Password",
                onNewValue = onPasswordChange,
            )
            Row(
                modifier = Modifier.padding(top = 10.dp, start = 20.dp, end = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                JWSwitchButton(
                    checked = uiState.isRememberCheckboxChecked,
                    onCheckedChange = onRememberMeCheckboxClick,
                    checkedColor = Black,
                )
                Text(
                    modifier = Modifier.padding(start = 5.dp),
                    text = "Remember Me",
                    fontFamily = FontFamily(
                        Font(
                            R.font.tt_medium,
                        ),
                    ),
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    style = JustWatchTheme.typography.label,
                    text = "Forgot password?",
                    color = Gray,
                    textAlign = TextAlign.End,
                    fontSize = 15.sp,
                )
            }
            JWButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, start = 10.dp, end = 10.dp)
                    .height(52.dp),
                text = "Sign In",
                textColor = White,
                backgroundColor = Black,
                onClick = onLoginClick,
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, top = 70.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                HorizontalDivider(
                    modifier = Modifier
                        .weight(1f)
                        .height(1.dp),
                    color = Color.LightGray,
                )
                Text(
                    text = "Or Continue with",
                    color = Gray,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(horizontal = 8.dp),
                )
                HorizontalDivider(
                    modifier = Modifier
                        .weight(1f)
                        .height(1.dp),
                    color = Color.LightGray,
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 70.dp),
                horizontalArrangement = Arrangement.Center,
            ) {
                // Google Button
                IconButton(
                    onClick = {},
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .border(1.dp, color = JustWatchTheme.colors.secondaryContainer, shape = CircleShape)
                        .background(JustWatchTheme.colors.secondaryContainer),
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_google),
                        contentDescription = "Google Login",
                        modifier = Modifier.size(20.dp),
                    )
                }
                Spacer(modifier = Modifier.width(5.dp))
                // Apple Button
                IconButton(
                    onClick = {},
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .border(1.dp, color = JustWatchTheme.colors.secondaryContainer, shape = CircleShape)
                        .background(JustWatchTheme.colors.secondaryContainer),
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_apple),
                        contentDescription = "Apple Login",
                        modifier = Modifier.size(20.dp),
                    )
                }
            }

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
            )
            Text(
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 50.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                    ) {
                        onEntryAsGuestClick.invoke()
                    },
                fontFamily = FontFamily(
                    Font(
                        R.font.tt_bold,
                    ),
                ),
                text = "Entry As Guest",
            )
        }
    }
}


@Preview
@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun LoginScreenPreview() {
    JustWatchTheme {
        LoginScreenContent(
            uiState = LoginUiState(),
        )
    }
}
