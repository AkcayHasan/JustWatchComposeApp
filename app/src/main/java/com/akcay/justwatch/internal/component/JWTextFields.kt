package com.akcay.justwatch.internal.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.akcay.justwatch.R
import com.akcay.justwatch.ui.theme.BorderGray
import com.akcay.justwatch.ui.theme.LightGray
import com.akcay.justwatch.R.drawable as AppIcon

@Composable
fun JWPasswordField(
  modifier: Modifier = Modifier,
  value: String,
  label: String,
  onNewValue: (String) -> Unit,
  isVisible: Boolean = false,
  visibilityClick: () -> Unit
) {
  val icon =
    if (isVisible) painterResource(id = AppIcon.ic_visibility_on)
    else painterResource(id = AppIcon.ic_visibility_off)

  val visualTransform =
    if (isVisible) VisualTransformation.None else PasswordVisualTransformation()

  TextField(
    value = value,
    onValueChange = onNewValue,
    label = {
      Text(
        text = label,
        color = Color.Gray
      )
    },
    trailingIcon = {
      IconButton(onClick = visibilityClick) {
        Icon(painter = icon, "Visibility", tint = Color.Gray)
      }
    },
    visualTransformation = visualTransform,
    maxLines = 1,
    singleLine = true,
    textStyle = MaterialTheme.typography.bodyMedium,
    modifier = modifier
      .fillMaxWidth()
      .padding(0.dp)
      .clip(RoundedCornerShape(10.dp))
      .border(1.dp, color = BorderGray, shape = RoundedCornerShape(10.dp)),
    colors = TextFieldDefaults.colors(
      focusedIndicatorColor = Color.Transparent,
      unfocusedIndicatorColor = Color.Transparent,
      disabledIndicatorColor = Color.Transparent,
      cursorColor = Color.Black,
      focusedLabelColor = LightGray,
      unfocusedLabelColor = LightGray,
      unfocusedContainerColor = LightGray
    )
  )
}

@Composable
fun JWTextField(
  modifier: Modifier = Modifier,
  value: String,
  label: String,
  onNewValue: (String) -> Unit
) {
  TextField(
    value = value,
    onValueChange = onNewValue,
    label = {
      Text(
        text = label,
        color = Color.Gray
      )
    },
    maxLines = 1,
    singleLine = true,
    textStyle = MaterialTheme.typography.bodyMedium,
    modifier = modifier
      .fillMaxWidth()
      .padding(0.dp)
      .clip(RoundedCornerShape(10.dp))
      .border(1.dp, color = BorderGray, shape = RoundedCornerShape(10.dp)),
    colors = TextFieldDefaults.colors(
      focusedIndicatorColor = Color.Transparent,
      unfocusedIndicatorColor = Color.Transparent,
      disabledIndicatorColor = Color.Transparent,
      cursorColor = Color.Black,
      focusedLabelColor = LightGray,
      unfocusedLabelColor = LightGray,
      unfocusedContainerColor = LightGray
    )
  )
}

@Composable
fun JWRichText(
  modifier: Modifier = Modifier,
  fontSize: TextUnit = 14.sp,
  normalText: String,
  clickableText: String,
  normalTextColor: Color = Color.Gray,
  clickableTextColor: Color = Color.Black,
  clickableFontWeight: FontWeight = FontWeight.Bold,
  onClick: () -> Unit,
  layoutResult: TextLayoutResult?,
  onLayoutResultChange: (TextLayoutResult) -> Unit
) {

  val annotatedString = buildAnnotatedString {
    withStyle(style = SpanStyle(color = normalTextColor)) {
      append(normalText)
    }
    pushStringAnnotation(tag = "clickableArea", annotation = "clickable_area")
    withStyle(
      style = SpanStyle(
        color = clickableTextColor,
        fontWeight = clickableFontWeight
      )
    ) {
      append(clickableText)
    }
    pop()
  }
  Text(
    modifier = modifier.pointerInput(Unit) {
      detectTapGestures { offset ->
        layoutResult?.let { textLayoutResult ->
          val position = textLayoutResult.getOffsetForPosition(offset)
          annotatedString.getStringAnnotations(
            start = position,
            end = position
          ).firstOrNull {
            it.tag.startsWith("clickableArea")
          }?.let {
            onClick.invoke()
          }
        }
      }
    },
    text = annotatedString,
    style = TextStyle(
      fontSize = fontSize, fontFamily = FontFamily(
        Font(
          R.font.tt_medium
        )
      )
    ),
    onTextLayout = { onLayoutResultChange(it) }
  )
}

@Preview
@Composable
fun EdittextPreview(modifier: Modifier = Modifier) {
  Box(modifier = Modifier.background(Color.White)) {
    TextField(
      value = "email",
      onValueChange = { },
      label = {
        Text(
          text = "E-mail",
          color = Color.Gray
        )
      },
      placeholder = { Text(text = "name@gmail.com") },
      singleLine = true,
      textStyle = MaterialTheme.typography.bodyMedium,
      modifier = Modifier
        .fillMaxWidth()
        .padding(5.dp)
        .clip(RoundedCornerShape(10.dp))
        .border(1.dp, color = BorderGray, shape = RoundedCornerShape(10.dp)),
      colors = TextFieldDefaults.colors(
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent,
        cursorColor = Color.Black,
        focusedLabelColor = LightGray,
        unfocusedLabelColor = LightGray,
        unfocusedContainerColor = LightGray
      )
    )
  }
}

@Preview
@Composable
fun TextPreview(modifier: Modifier = Modifier) {
  val annotatedString = buildAnnotatedString {
    // Normal metin
    withStyle(style = SpanStyle(color = Color.Gray)) {
      append("By continuing, you accept our ")
    }
    // Tıklanabilir kalın metin
    pushStringAnnotation(tag = "PrivacyPolicy", annotation = "privacy_policy")
    withStyle(
      style = SpanStyle(
        color = Color.Black,
      )
    ) {
      append("Privacy Policy")
    }
    pop()
  }
  Box(
    modifier = Modifier
      .background(Color.White)
      .fillMaxWidth()
      .height(100.dp)
  ) {
    Text(
      text = annotatedString,
      style = TextStyle(fontSize = 14.sp),
      modifier = Modifier
        .padding(8.dp)
        .clickable { },
      onTextLayout = {
        val annotation = annotatedString.getStringAnnotations(
          tag = "PrivacyPolicy",
          start = 0,
          end = annotatedString.length
        ).firstOrNull()

        annotation?.let {

        }
      },
    )
  }
}