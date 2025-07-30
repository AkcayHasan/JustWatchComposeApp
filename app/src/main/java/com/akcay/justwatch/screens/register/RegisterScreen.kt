package com.akcay.justwatch.screens.register

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.akcay.justwatch.R
import com.akcay.justwatch.internal.component.JWButton
import com.akcay.justwatch.internal.component.JWPasswordField
import com.akcay.justwatch.internal.component.JWRoundedCheckBox
import com.akcay.justwatch.internal.component.JWTextField

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RegisterScreen(
    navigateHome: () -> Unit,
    navigateBack: () -> Unit,
    viewModel: RegisterViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState

    RegisterScreenContent(
        uiState = uiState,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onRepeatPasswordChange = viewModel::onRepeatPasswordChange,
        onRegisterClick = navigateHome,
        onLoginClick = navigateBack,
    )
}

@OptIn(ExperimentalLayoutApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RegisterScreenContent(
    uiState: RegisterUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onRepeatPasswordChange: (String) -> Unit,
    onRegisterClick: () -> Unit,
    onLoginClick: () -> Unit,
) {
    val scrollState = rememberScrollState()
    val isImeVisible = WindowInsets.isImeVisible

    Column(
        modifier = Modifier
          .fillMaxSize()
          .imePadding()
          .then(
            if (isImeVisible) Modifier.verticalScroll(scrollState)
            else Modifier,
          )
          .padding(top = 100.dp),
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
            text = "Sign Up",
            textAlign = TextAlign.Center,
        )
        JWTextField(
            modifier = Modifier
              .fillMaxWidth()
              .padding(all = 10.dp),
            label = "E-Mail",
            value = uiState.email,
            onNewValue = onEmailChange,
        )
        JWPasswordField(
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 10.dp),
            value = uiState.password,
            label = "Password",
            onNewValue = onPasswordChange,
        )
        JWPasswordField(
            modifier = Modifier
              .fillMaxWidth()
              .padding(top = 10.dp, start = 10.dp, end = 10.dp),
            value = uiState.repeatPassword,
            label = "Confirm Password",
            onNewValue = onRepeatPasswordChange,
        )
        JWRoundedCheckBox(
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 20.dp, vertical = 10.dp),
            label = "Please make sure the passwords match!",
            isChecked = uiState.isPasswordMatches,
        )
        JWButton(
            modifier = Modifier
              .fillMaxWidth()
              .padding(top = 20.dp, start = 10.dp, end = 10.dp)
              .height(52.dp),
            text = "Sign Up",
            textColor = White,
            enabled = uiState.isPasswordMatches,
            backgroundColor = Black,
            onClick = {
                onRegisterClick.invoke()
            },
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
                color = LightGray,
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
                color = LightGray,
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
                  .border(1.dp, color = Gray, shape = CircleShape)
                  .background(LightGray),
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
                  .border(1.dp, color = Gray, shape = CircleShape)
                  .background(LightGray),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_apple),
                    contentDescription = "Apple Login",
                    modifier = Modifier.size(20.dp),
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))


    }
}
