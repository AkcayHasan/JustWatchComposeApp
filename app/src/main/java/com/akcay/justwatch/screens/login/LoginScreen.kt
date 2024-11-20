package com.akcay.justwatch.screens.login

import com.akcay.justwatch.internal.component.JWSwitchButton
import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Scaffold
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
import com.akcay.justwatch.R
import com.akcay.justwatch.internal.component.EmailField
import com.akcay.justwatch.R.drawable as AppIcon
import com.akcay.justwatch.internal.component.JWRoundedCheckBox
import com.akcay.justwatch.internal.component.PasswordField
import com.akcay.justwatch.internal.ext.isValidEmail
import com.akcay.justwatch.internal.navigation.Screen
import com.akcay.justwatch.internal.component.JWDialogBox
import com.akcay.justwatch.internal.component.JWDialogBoxModel
import com.akcay.justwatch.ui.theme.Green2
import com.akcay.justwatch.ui.theme.LightBlue

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LoginScreen(
  navigate: (String) -> Unit,
  navigateAndPopUp: (String, String) -> Unit,
  viewModel: LoginViewModel = hiltViewModel()
) {
  val uiState by viewModel.uiState.collectAsState()
  val dialogState by viewModel.dialogState

  var passwordVisible by remember { mutableStateOf(false) }
  var isRememberMeChecked by remember { mutableStateOf(false) }
  val scrollState = rememberScrollState()
  val isImeVisible = WindowInsets.isImeVisible

  LaunchedEffect(uiState.isRememberCheckboxChecked) {
    isRememberMeChecked = uiState.isRememberCheckboxChecked
  }

  LoginScreenContent(
    uiState = uiState,
    dialogState = dialogState,
    scrollState = scrollState,
    isImeVisible = isImeVisible,
    onForgotPasswordClick = { navigate.invoke("") },
    onEntryAsGuestClick = { viewModel.createAnonymousUser(navigateAndPopUp) },
    onEmailChange = viewModel::onEmailChange,
    onPasswordChange = viewModel::onPasswordChange,
    onPasswordVisible = passwordVisible,
    onTogglePasswordVisibility = { passwordVisible = !passwordVisible },
    isRememberMeChecked = isRememberMeChecked,
    onRememberMeCheckboxClick = { isRememberMeChecked = !isRememberMeChecked },
    onLoginClick = {
      viewModel.onLoginClick(navigateAndPopUp, isRememberMeChecked)
    },
    onSignUpClick = { navigate.invoke(Screen.Register.route) },
    onPositiveDialogButtonClick = { viewModel.closeDialog() }
  )
}

@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun LoginScreenContent(
  uiState: LoginUiState,
  dialogState: JWDialogBoxModel?,
  scrollState: ScrollState,
  isImeVisible: Boolean,
  onEmailChange: (String) -> Unit,
  onPasswordChange: (String) -> Unit,
  onPasswordVisible: Boolean,
  onTogglePasswordVisibility: () -> Unit,
  isRememberMeChecked: Boolean,
  onRememberMeCheckboxClick: (Boolean) -> Unit,
  onLoginClick: () -> Unit,
  onSignUpClick: () -> Unit,
  onForgotPasswordClick: () -> Unit,
  onEntryAsGuestClick: () -> Unit,
  onPositiveDialogButtonClick: () -> Unit
) {

  Box(
    modifier = Modifier
      .fillMaxSize()
      .imePadding()
      .verticalScroll(state = scrollState, enabled = isImeVisible)
  ) {
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
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 20.dp, vertical = 10.dp),
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
        isVisible = onPasswordVisible,
        visibilityClick = onTogglePasswordVisibility
      )
      Row(
        modifier = Modifier.padding(top = 10.dp, start = 20.dp, end = 20.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        JWSwitchButton(checked = isRememberMeChecked, onCheckedChange = onRememberMeCheckboxClick)
        Text(
          modifier = Modifier.padding(start = 5.dp),
          text = "Remember Me", fontFamily = FontFamily(
            Font(
              R.font.tt_medium
            )
          )
        )
        Text(
          modifier = Modifier
            .fillMaxWidth(),
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
            .clickable(
              interactionSource = remember { MutableInteractionSource() },
              indication = null
            ) {
              onSignUpClick.invoke()
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
          .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null
          ) {
            onEntryAsGuestClick.invoke()
          }, fontFamily = FontFamily(
          Font(
            R.font.tt_bold
          )
        ), text = "Entry As Guest"
      )
    }

    if (dialogState != null) {
      JWDialogBox(
        onDismissRequest = { },
        content = JWDialogBoxModel(
          mainColor = dialogState.mainColor,
          title = dialogState.title,
          description = dialogState.description,
          positiveButtonText = "Ok"
        ),
        positiveButtonClickAction = onPositiveDialogButtonClick
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
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 20.dp, vertical = 10.dp),
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
      Row(
        modifier = Modifier.padding(top = 10.dp, start = 20.dp, end = 20.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        JWSwitchButton(checked = true, onCheckedChange = {})
        Text(
          modifier = Modifier.padding(start = 5.dp),
          text = "Remember Me", fontFamily = FontFamily(
            Font(
              R.font.tt_medium
            )
          )
        )
        Text(
          modifier = Modifier
            .fillMaxWidth(),
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