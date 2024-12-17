package com.akcay.justwatch.screens.register

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.akcay.justwatch.R.string as AppText
import androidx.hilt.navigation.compose.hiltViewModel
import com.akcay.justwatch.R
import com.akcay.justwatch.internal.component.JWButton
import com.akcay.justwatch.internal.component.JWPasswordField
import com.akcay.justwatch.internal.component.JWRichText
import com.akcay.justwatch.internal.component.JWRoundedCheckBox
import com.akcay.justwatch.internal.component.JWTextField
import com.akcay.justwatch.internal.ext.passwordMatches
import com.akcay.justwatch.internal.navigation.Screen
import com.akcay.justwatch.ui.theme.Green2
import com.akcay.justwatch.ui.theme.LightBlue

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RegisterScreen(
  navigateAndPopUp: (String, String) -> Unit,
  viewModel: RegisterViewModel = hiltViewModel()
) {
  val uiState by viewModel.uiState

  var layoutResult by remember { mutableStateOf<TextLayoutResult?>(null) }
  val scrollState = rememberScrollState()
  val isImeVisible = WindowInsets.isImeVisible

  RegisterScreenContent(
    uiState = uiState,
    scrollState = scrollState,
    isImeVisible = isImeVisible,
    onEmailChange = viewModel::onEmailChange,
    onPasswordChange = viewModel::onPasswordChange,
    onRepeatPasswordChange = viewModel::onRepeatPasswordChange,
    onRegisterClick = { viewModel.onRegisterClick(navigateAndPopUp) },
    onLoginClick = { navigateAndPopUp.invoke(Screen.Login.route, Screen.Register.route) },
    layoutResult = layoutResult,
    onLayoutResultChange = { newLayoutResult ->
      layoutResult = newLayoutResult
    }
  )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RegisterScreenContent(
  uiState: RegisterUiState,
  scrollState: ScrollState,
  isImeVisible: Boolean,
  onEmailChange: (String) -> Unit,
  onPasswordChange: (String) -> Unit,
  onRepeatPasswordChange: (String) -> Unit,
  onRegisterClick: () -> Unit,
  onLoginClick: () -> Unit,
  layoutResult: TextLayoutResult?,
  onLayoutResultChange: (TextLayoutResult) -> Unit
) {
  var isPasswordVisibility by remember {
    mutableStateOf(false)
  }

  var isRepeatPasswordVisibility by remember {
    mutableStateOf(false)
  }

  val passwordMatches =
    uiState.password.isNotEmpty() && uiState.repeatPassword.passwordMatches(uiState.password)

  Box(
    modifier = Modifier
      .fillMaxSize()
      .imePadding()
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .then(
          if (isImeVisible) Modifier.verticalScroll(scrollState)
          else Modifier
        )
        .padding(top = 100.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Text(
        modifier = Modifier
          .fillMaxWidth(),
        fontFamily = FontFamily(
          Font(
            R.font.tt_medium
          )
        ), color = Black,
        fontSize = 32.sp,
        text = "Sign Up",
        textAlign = TextAlign.Center
      )
      JWTextField(
        modifier = Modifier
          .fillMaxWidth()
          .padding(all = 10.dp),
        label = "E-Mail",
        value = uiState.email,
        onNewValue = onEmailChange
      )
      JWPasswordField(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 10.dp),
        value = uiState.password,
        label = "Password",
        onNewValue = onPasswordChange,
        isVisible = isPasswordVisibility,
        visibilityClick = { isPasswordVisibility = !isPasswordVisibility }
      )
      JWPasswordField(
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 10.dp, start = 10.dp, end = 10.dp),
        value = uiState.repeatPassword,
        label = "Confirm Password",
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
      JWButton(
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 20.dp, start = 10.dp, end = 10.dp)
          .height(52.dp),
        text = "Sign Up",
        textColor = White,
        enabled = passwordMatches,
        backgroundColor = Black,
        onClick = {
          onRegisterClick.invoke()
        }
      )
      JWRichText(
        modifier = Modifier.padding(top = 15.dp),
        normalText = "By continuning, you accept our ",
        clickableText = "Privacy Policy",
        clickableFontWeight = FontWeight.Normal,
        layoutResult = layoutResult,
        onLayoutResultChange = onLayoutResultChange,
        onClick = {

        }
      )

      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(start = 20.dp, end = 20.dp, top = 70.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        HorizontalDivider(
          modifier = Modifier
            .weight(1f)
            .height(1.dp),
          color = LightGray
        )
        Text(
          text = "Or Continue with",
          color = Gray,
          fontSize = 14.sp,
          modifier = Modifier.padding(horizontal = 8.dp)
        )
        HorizontalDivider(
          modifier = Modifier
            .weight(1f)
            .height(1.dp),
          color = LightGray
        )
      }

      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 70.dp),
        horizontalArrangement = Arrangement.Center
      ) {
        // Google Button
        IconButton(
          onClick = {},
          modifier = Modifier
            .size(64.dp)
            .clip(CircleShape)
            .border(1.dp, color = Gray, shape = CircleShape)
            .background(LightGray)
        ) {
          Image(
            painter = painterResource(id = R.drawable.ic_google),
            contentDescription = "Google Login",
            modifier = Modifier.size(20.dp)
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
            .background(LightGray)
        ) {
          Image(
            painter = painterResource(id = R.drawable.ic_apple),
            contentDescription = "Apple Login",
            modifier = Modifier.size(20.dp)
          )
        }
      }

      Spacer(modifier = Modifier.weight(1f))

      JWRichText(
        modifier = Modifier.padding(bottom = 50.dp),
        normalText = "Already have an account? ",
        clickableText = "Sign In",
        layoutResult = layoutResult,
        onLayoutResultChange = onLayoutResultChange,
        onClick = {
          onLoginClick.invoke()
        }
      )
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
        .padding(top = 100.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Text(
        modifier = Modifier
          .fillMaxWidth(),
        fontFamily = FontFamily(
          Font(
            R.font.tt_medium
          )
        ), color = Black,
        fontSize = 32.sp,
        text = "Sign Up",
        textAlign = TextAlign.Center
      )
      JWTextField(
        modifier = Modifier
          .fillMaxWidth()
          .padding(all = 10.dp),
        label = "E-Mail",
        value = "hasan@gmail.com",
        onNewValue = {}
      )
      JWPasswordField(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 10.dp),
        value = "asdasd",
        label = "Password",
        onNewValue = {},
        isVisible = false,
        visibilityClick = {}
      )
      JWPasswordField(
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 10.dp, start = 10.dp, end = 10.dp),
        value = "asdasd",
        label = "Confirm Password",
        onNewValue = {},
        isVisible = false,
        visibilityClick = {}
      )
      JWRoundedCheckBox(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 20.dp, vertical = 10.dp),
        label = "Please make sure the passwords match!",
        isChecked = true
      )
      JWButton(
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 20.dp, start = 10.dp, end = 10.dp)
          .height(52.dp),
        text = "Sign Up",
        textColor = White,
        backgroundColor = Black,
        onClick = {}
      )
      JWRichText(
        modifier = Modifier.padding(top = 15.dp),
        normalText = "By continuning, you accept our ",
        clickableText = "Privacy Policy",
        layoutResult = null,
        onLayoutResultChange = {},
        onClick = {}
      )

      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(start = 20.dp, end = 20.dp, top = 70.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        HorizontalDivider(
          modifier = Modifier
            .weight(1f)
            .height(1.dp),
          color = LightGray
        )
        Text(
          text = "Or Continue with",
          color = Gray,
          fontSize = 14.sp,
          modifier = Modifier.padding(horizontal = 8.dp)
        )
        HorizontalDivider(
          modifier = Modifier
            .weight(1f)
            .height(1.dp),
          color = LightGray
        )
      }

      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 70.dp),
        horizontalArrangement = Arrangement.Center
      ) {
        // Google Button
        IconButton(
          onClick = {},
          modifier = Modifier
            .size(64.dp)
            .clip(CircleShape)
            .border(1.dp, color = Gray, shape = CircleShape)
            .background(LightGray) // Hafif gri arka plan
        ) {
          Image(
            painter = painterResource(id = R.drawable.ic_google), // Google ikonunuzu ekleyin
            contentDescription = "Google Login",
            modifier = Modifier.size(20.dp)
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
            .background(LightGray) // Hafif gri arka plan
        ) {
          Image(
            painter = painterResource(id = R.drawable.ic_apple), // Apple ikonunuzu ekleyin
            contentDescription = "Apple Login",
            modifier = Modifier.size(20.dp)
          )
        }
      }
      Spacer(modifier = Modifier.weight(1f))
      JWRichText(
        modifier = Modifier.padding(bottom = 50.dp),
        normalText = "Already have an account? ",
        clickableText = "Sign In",
        layoutResult = null,
        onLayoutResultChange = {},
        onClick = {}
      )

    }
  }
}