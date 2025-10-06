package com.akcay.justwatch.screens.forgotpassword

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.akcay.justwatch.internal.component.JWTextField
import com.akcay.justwatch.ui.theme.JustWatchTheme

@Composable
fun ForgotPasswordScreen(
    navigateBack: () -> Unit,
    viewModel: ForgotPasswordViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.channel.collect { event ->
            when (event) {
                ForgotPasswordScreenViewModelEvent.ShowSuccessMessage -> {
                    showDialog = true
                }
            }
        }
    }

    ForgotPasswordScreenContent(
        uiState = uiState,
        onBackClick = navigateBack,
        onEmailChange = viewModel::onEmailChange,
        onSendResetEmailClick = { viewModel.sendEvent(ForgotPasswordScreenViewEvent.OnSendResetEmailClicked) },
    )

    if (showDialog) {
        JWDialogBox(
            onDismissRequest = { showDialog = false },
            content = JWDialogBoxModel(
                mainColor = JustWatchTheme.colors.onSurfaceVariant,
                title = "Email Sent",
                description = "Password reset instructions have been sent to your email address.",
                positiveButtonText = "Ok",
            ),
            positiveButtonClickAction = {
                showDialog = false
                navigateBack()
            },
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ForgotPasswordScreenContent(
    uiState: ForgotPasswordUiState,
    onBackClick: () -> Unit = {},
    onEmailChange: (String) -> Unit = {},
    onSendResetEmailClick: () -> Unit = {},
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
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                fontFamily = FontFamily(
                    Font(
                        R.font.tt_medium,
                    ),
                ),
                color = JustWatchTheme.colors.onSurface,
                fontSize = 32.sp,
                text = "Forgot Password",
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                fontFamily = FontFamily(
                    Font(
                        R.font.tt_regular,
                    ),
                ),
                color = JustWatchTheme.colors.onSurfaceVariant,
                fontSize = 16.sp,
                text = "Enter your email address and we'll send you instructions to reset your password.",
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(40.dp))

            JWTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 10.dp),
                value = uiState.email,
                label = "E-mail",
                onNewValue = onEmailChange,
            )

            Spacer(modifier = Modifier.height(20.dp))

            JWButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, start = 10.dp, end = 10.dp)
                    .height(52.dp),
                text = "Send Reset Email",
                textColor = JustWatchTheme.colors.onPrimaryContainer,
                backgroundColor = JustWatchTheme.colors.primaryContainer,
                onClick = onSendResetEmailClick,
            )

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
                text = "Back to Login",
            )
        }
    }
}

@Preview
@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun ForgotPasswordScreenPreview() {
    JustWatchTheme {
        ForgotPasswordScreenContent(
            uiState = ForgotPasswordUiState(),
        )
    }
}
