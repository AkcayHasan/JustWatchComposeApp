package com.akcay.justwatch.screens.register

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
import androidx.compose.runtime.remember
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
import org.koin.androidx.compose.koinViewModel
import com.akcay.justwatch.R
import com.akcay.justwatch.internal.component.JWButton
import com.akcay.justwatch.internal.component.JWDialogBox
import com.akcay.justwatch.internal.component.JWDialogBoxModel
import com.akcay.justwatch.internal.component.JWLoadingView
import com.akcay.justwatch.internal.component.JWPasswordField
import com.akcay.justwatch.internal.component.JWTextField
import com.akcay.justwatch.internal.util.FieldKey
import com.akcay.justwatch.ui.theme.JustWatchTheme

@Composable
fun RegisterScreen(
    navigateBack: () -> Unit,
    navigateMovies: () -> Unit,
    viewModel: RegisterViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.channel.collect { event ->
            when (event) {
                RegisterScreenViewModelEvent.NavigateMoviesScreen -> navigateMovies()
            }
        }
    }
    
    // Clear validation errors when user starts typing
    LaunchedEffect(uiState.email, uiState.password, uiState.confirmPassword, uiState.name, uiState.surname) {
        if (uiState.validationState.fields.isNotEmpty()) {
            viewModel.clearValidationErrors()
        }
    }

    RegisterScreenContent(
        uiState = uiState,
        onBackClick = navigateBack,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onConfirmPasswordChange = viewModel::onConfirmPasswordChange,
        onNameChange = viewModel::onNameChange,
        onSurnameChange = viewModel::onSurnameChange,
        onRegisterClick = { viewModel.sendEvent(RegisterScreenViewEvent.OnRegisterClicked) },
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
fun RegisterScreenContent(
    uiState: RegisterUiState,
    onBackClick: () -> Unit = {},
    onEmailChange: (String) -> Unit = {},
    onPasswordChange: (String) -> Unit = {},
    onConfirmPasswordChange: (String) -> Unit = {},
    onNameChange: (String) -> Unit = {},
    onSurnameChange: (String) -> Unit = {},
    onRegisterClick: () -> Unit = {},
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
                text = "Create Account",
                textAlign = TextAlign.Center,
            )
            
            // Name and Surname Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(10.dp)
            ) {
                JWTextField(
                    modifier = Modifier.weight(1f),
                    value = uiState.name,
                    label = "Name",
                    onNewValue = onNameChange,
                    hasError = uiState.validationState.fields[FieldKey.NAME]?.hasError ?: false,
                    errorMessage = uiState.validationState.fields[FieldKey.NAME]?.errorMessage,
                )
                JWTextField(
                    modifier = Modifier.weight(1f),
                    value = uiState.surname,
                    label = "Surname",
                    onNewValue = onSurnameChange,
                    hasError = uiState.validationState.fields[FieldKey.SURNAME]?.hasError ?: false,
                    errorMessage = uiState.validationState.fields[FieldKey.SURNAME]?.errorMessage,
                )
            }
            
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
            JWPasswordField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, start = 10.dp, end = 10.dp),
                value = uiState.confirmPassword,
                label = "Confirm Password",
                onNewValue = onConfirmPasswordChange,
                hasError = uiState.validationState.fields[FieldKey.CONFIRM_PASSWORD]?.hasError ?: false,
                errorMessage = uiState.validationState.fields[FieldKey.CONFIRM_PASSWORD]?.errorMessage,
            )
            
            JWButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, start = 10.dp, end = 10.dp)
                    .height(52.dp),
                text = "Create Account",
                textColor = JustWatchTheme.colors.onPrimaryContainer,
                backgroundColor = JustWatchTheme.colors.primaryContainer,
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
                    contentDescription = "Google Register",
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
                        onBackClick.invoke()
                    },
                fontFamily = FontFamily(
                    Font(
                        R.font.tt_bold,
                    ),
                ),
                color = JustWatchTheme.colors.primary,
                text = "Already have an account? Sign In",
            )
        }
    }
}

@Preview
@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun RegisterScreenPreview() {
    JustWatchTheme {
        RegisterScreenContent(
            uiState = RegisterUiState(),
        )
    }
}
