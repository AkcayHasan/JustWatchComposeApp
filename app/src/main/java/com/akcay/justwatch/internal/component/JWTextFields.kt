package com.akcay.justwatch.internal.component

import androidx.annotation.StringRes
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.akcay.justwatch.internal.ext.mutate
import com.akcay.justwatch.ui.theme.JustWatchTheme
import com.akcay.justwatch.R.drawable as AppIcon

@Composable
fun JWPasswordField(
    value: String,
    label: String,
    onNewValue: (String) -> Unit,
    modifier: Modifier = Modifier,
    hasError: Boolean = false,
    errorMessage: String? = null,
) {
    var isVisible by remember { mutableStateOf(false) }

    val icon =
        if (isVisible) painterResource(id = AppIcon.ic_visibility_on)
        else painterResource(id = AppIcon.ic_visibility_off)

    val visualTransform =
        if (isVisible) VisualTransformation.None else PasswordVisualTransformation()

    Column(modifier = modifier) {
        TextField(
            value = value,
            onValueChange = onNewValue,
            label = {
                Text(
                    text = label,
                    color = if (hasError) JustWatchTheme.colors.error else JustWatchTheme.colors.onSurfaceVariant,
                )
            },
            trailingIcon = {
                IconButton(onClick = { isVisible = !isVisible } ) {
                    Icon(painter = icon, "Visibility", tint = JustWatchTheme.colors.onSurfaceVariant)
                }
            },
            visualTransformation = visualTransform,
            maxLines = 1,
            singleLine = true,
            textStyle = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
              .fillMaxWidth()
              .padding(0.dp)
              .clip(RoundedCornerShape(10.dp))
              .border(
                  1.dp, 
                  color = if (hasError) JustWatchTheme.colors.error else JustWatchTheme.colors.onSurfaceVariant, 
                  shape = RoundedCornerShape(10.dp)
              ),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                cursorColor = JustWatchTheme.colors.primary,
                focusedLabelColor = if (hasError) JustWatchTheme.colors.error else JustWatchTheme.colors.primary,
                unfocusedLabelColor = if (hasError) JustWatchTheme.colors.error else JustWatchTheme.colors.primary,
                unfocusedContainerColor = JustWatchTheme.colors.surface,
            ),
        )
        
        if (hasError && !errorMessage.isNullOrBlank()) {
            Text(
                text = errorMessage,
                color = JustWatchTheme.colors.error,
                style = JustWatchTheme.typography.label,
                modifier = Modifier.padding(top = 4.dp, start = 12.dp)
            )
        }
    }
}

@Composable
fun JWTextField(
    modifier: Modifier = Modifier,
    value: String,
    label: String,
    onNewValue: (String) -> Unit,
    hasError: Boolean = false,
    errorMessage: String? = null,
) {
    Column(modifier = modifier) {
        TextField(
            value = value,
            onValueChange = onNewValue,
            label = {
                Text(
                    text = label,
                    color = if (hasError) JustWatchTheme.colors.error else JustWatchTheme.colors.onSurfaceVariant,
                )
            },
            maxLines = 1,
            singleLine = true,
            textStyle = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
              .fillMaxWidth()
              .padding(0.dp)
              .clip(RoundedCornerShape(10.dp))
              .border(
                  1.dp, 
                  color = if (hasError) JustWatchTheme.colors.error else JustWatchTheme.colors.onSurfaceVariant, 
                  shape = RoundedCornerShape(10.dp)
              ),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                cursorColor = JustWatchTheme.colors.primary,
                focusedLabelColor = if (hasError) JustWatchTheme.colors.error else JustWatchTheme.colors.primary,
                unfocusedLabelColor = if (hasError) JustWatchTheme.colors.error else JustWatchTheme.colors.primary,
                unfocusedContainerColor = JustWatchTheme.colors.surface,
            ),
        )
        
        if (hasError && !errorMessage.isNullOrBlank()) {
            Text(
                text = errorMessage,
                color = JustWatchTheme.colors.error,
                style = JustWatchTheme.typography.label,
                modifier = Modifier.padding(top = 4.dp, start = 12.dp)
            )
        }
    }
}

@Composable
fun JWRichText(
    richText: RichText,
    navigateScreen: (NavigationCode) -> Unit,
) {
    val resources = LocalContext.current.resources
    AnnotatedString.fromHtml(resources.getString(richText.text)).mutate { text ->
        text.spanStyles.forEachIndexed { index, range ->
            addLink(
                url = LinkAnnotation.Url(
                    url = "",
                    styles = TextLinkStyles(
                        style = range.item.copy(color = Color.Magenta)
                    ),
                    linkInteractionListener = {
                        navigateScreen.invoke(richText.code[index])
                    }
                ),
                start = range.start,
                end = range.end,
            )
        }
    }
}

data class RichText(
    @param:StringRes val text: Int,
    val code: List<NavigationCode>
)

enum class NavigationCode {
    LOGIN,
    REGISTER,
}
