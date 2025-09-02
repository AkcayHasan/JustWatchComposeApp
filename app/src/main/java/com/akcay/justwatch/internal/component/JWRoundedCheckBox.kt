package com.akcay.justwatch.internal.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun JWRoundedCheckBox(
    modifier: Modifier = Modifier,
    label: String,
    isChecked: Boolean,
    isClickable: Boolean = false,
    checkedColor: Color = Color.Black,
    unCheckedColor: Color = Color.White,
    onValueChange: ((Boolean) -> Unit)? = null
) {
    val checkBoxColor: Color by animateColorAsState(
        targetValue = if (isChecked) checkedColor else unCheckedColor,
        animationSpec = tween(
            durationMillis = 500,
            easing = LinearEasing,
        ), label = ""
    )

    val checkIconAlpha: Float by animateFloatAsState(
        targetValue = if (isChecked) 1f else 0f,
        animationSpec = tween(
            durationMillis = 500,
            easing = LinearEasing,
        ), label = ""
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .wrapContentHeight()
            .toggleable(
                value = true,
                role = Role.Checkbox,
                onValueChange = onValueChange ?: {},
                enabled = isClickable
            )
    ) {
        Box(
            modifier = Modifier
                .size(20.dp)
                .background(color = checkBoxColor, shape = RoundedCornerShape(15.dp))
                .border(width = 1.5.dp, color = Color.Black, shape = RoundedCornerShape(15.dp))
            ,
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.Check,
                contentDescription = null,
                tint = unCheckedColor,
                modifier = Modifier.alpha(checkIconAlpha).size(17.dp)
            )
        }
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = label,
            lineHeight = 15.sp,
            fontSize = 12.sp
        )
    }

}

@Composable
@Preview
fun JWRoundedCheckBoxPreview(

) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .wrapContentHeight()
            .toggleable(
                value = true,
                role = Role.Checkbox,
                onValueChange = {}
            )
    ) {
        Box(
            modifier = Modifier
                .size(20.dp)
                .background(color = Color.Black, shape = RoundedCornerShape(15.dp))
                .border(width = 1.5.dp, color = Color.Black, shape = RoundedCornerShape(15.dp))
            ,
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.Check,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.alpha(1f)
            )
        }
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = "Deneme",
            color = Color.White,
            fontSize = 12.sp
        )
    }

}
