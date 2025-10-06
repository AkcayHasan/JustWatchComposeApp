package com.akcay.justwatch.screens.login

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
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
import com.akcay.justwatch.internal.util.FieldKey
import com.akcay.justwatch.internal.util.SetSystemBarsForScreen
import com.akcay.justwatch.ui.theme.JustWatchTheme

@Composable
fun LoginScreen(
    navigateForgotPassword: () -> Unit,
    navigateRegister: () -> Unit,
    navigateMovies: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    SetSystemBarsForScreen() // Uses CompositionLocal automatically

    LaunchedEffect(Unit) {
        viewModel.channel.collect { event ->
            when (event) {
                LoginScreenViewModelEvent.NavigateMoviesScreen -> navigateMovies()
            }
        }
    }

    // Clear validation errors when user starts typing
    LaunchedEffect(uiState.email, uiState.password) {
        if (uiState.validationState.fields.isNotEmpty()) {
            viewModel.clearValidationErrors()
        }
    }

    LoginScreenContent(
        uiState = uiState,
        onForgotPasswordClick = navigateForgotPassword,
        onRegisterClick = navigateRegister,
        onEntryAsGuestClick = { navigateMovies() },
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onRememberMeCheckboxClick = viewModel::setRememberMeChecked,
        onLoginClick = { viewModel.sendEvent(LoginScreenViewEvent.OnLoginClicked) },
    )

    if (uiState.validationState.showDialog) {
        JWDialogBox(
            onDismissRequest = { viewModel.dismissDialog() },
            content = JWDialogBoxModel(
                mainColor = JustWatchTheme.colors.onSurfaceVariant,
                title = "Validation Error",
                description = uiState.validationState.dialogMessage ?: "Please check your input",
                positiveButtonText = "Ok",
            ),
            positiveButtonClickAction = { viewModel.dismissDialog() },
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
    onRegisterClick: () -> Unit = {},
    onForgotPasswordClick: () -> Unit = {},
    onEntryAsGuestClick: () -> Unit = {},
) {
    val scrollState = rememberScrollState()
    val focusManager = LocalFocusManager.current
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
                .imePadding()
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = { focusManager.clearFocus() },
                    )
                },
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
                color = JustWatchTheme.colors.onSurface,
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
                hasError = uiState.validationState.fields[FieldKey.EMAIL]?.hasError ?: false,
                errorMessage = uiState.validationState.fields[FieldKey.EMAIL]?.errorMessage,
            )
            JWPasswordField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, start = 10.dp, end = 10.dp),
                value = uiState.password,
                label = "Password",
                onNewValue = onPasswordChange,
                hasError = uiState.validationState.fields[FieldKey.PASSWORD]?.hasError ?: false,
                errorMessage = uiState.validationState.fields[FieldKey.PASSWORD]?.errorMessage,
            )
            Row(
                modifier = Modifier.padding(top = 10.dp, start = 20.dp, end = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                JWSwitchButton(
                    checked = uiState.isRememberCheckboxChecked,
                    onCheckedChange = onRememberMeCheckboxClick,
                    checkedColor = JustWatchTheme.colors.primary,
                )
                Text(
                    modifier = Modifier.padding(start = 5.dp),
                    text = "Remember Me",
                    fontFamily = FontFamily(
                        Font(
                            R.font.tt_medium,
                        ),
                    ),
                    color = JustWatchTheme.colors.onSurface,
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onForgotPasswordClick()
                        },
                    style = JustWatchTheme.typography.label,
                    text = "Forgot password?",
                    color = JustWatchTheme.colors.onSurfaceVariant,
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
                textColor = JustWatchTheme.colors.onPrimaryContainer,
                backgroundColor = JustWatchTheme.colors.primaryContainer,
                onClick = onLoginClick,
            )

            JWButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, start = 10.dp, end = 10.dp)
                    .height(52.dp)
                    .border(
                        width = 1.dp,
                        color = JustWatchTheme.colors.onPrimaryContainer,
                        shape = RoundedCornerShape(50.dp)
                    ),
                text = "Register",
                textColor = JustWatchTheme.colors.onPrimaryContainer,
                backgroundColor = JustWatchTheme.colors.onPrimary,
                onClick = onRegisterClick,
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
                    color = JustWatchTheme.colors.onSurfaceVariant,
                )
                Text(
                    text = "Or Continue with",
                    color = JustWatchTheme.colors.onSurfaceVariant,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(horizontal = 8.dp),
                )
                HorizontalDivider(
                    modifier = Modifier
                        .weight(1f)
                        .height(1.dp),
                    color = JustWatchTheme.colors.onSurfaceVariant,
                )
            }

            // Google Button
            IconButton(
                onClick = {},
                modifier = Modifier
                    .padding(20.dp)
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
                color = JustWatchTheme.colors.primary,
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
