package com.akcay.justwatch.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.akcay.justwatch.R
import com.akcay.justwatch.ui.theme.Red

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JWDialogBox(
    showDialog: Boolean,
    onDismissRequest: () -> Unit,
    content: JWDialogBoxModel,
    positiveButtonClickAction: (() -> Unit)? = null,
    negativeButtonClickAction: (() -> Unit)? = null,
) {
    if (showDialog) {
        BasicAlertDialog(onDismissRequest = { onDismissRequest.invoke() }, content = {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.White)
                    .wrapContentHeight()
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(bottom = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .height(100.dp)
                            .fillMaxWidth()
                            .background(content.mainColor),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            modifier = Modifier.height(50.dp),
                            painter = painterResource(id = R.drawable.attention),
                            contentDescription = ""
                        )
                    }
                    Text(
                        text = content.title,
                        modifier = Modifier.padding(top = 20.dp),
                        fontSize = 18.sp,
                        fontFamily = FontFamily(Font(R.font.tt_bold)),
                        color = content.mainColor
                    )
                    Text(
                        text = content.description,
                        modifier = Modifier.padding(top = 20.dp, start = 10.dp, end = 10.dp),
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.tt_regular)),
                        textAlign = TextAlign.Center,
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(70.dp)
                            .padding(top = 20.dp, start = 45.dp, end = 45.dp),
                        horizontalArrangement = if (negativeButtonClickAction != null) Arrangement.SpaceBetween else Arrangement.Center
                    ) {
                        if (content.negativeButtonText != null) {
                            Button(
                                modifier = Modifier
                                    .width(100.dp),
                                shape = RoundedCornerShape(10.dp),
                                border = BorderStroke(width = 1.dp, color = content.mainColor),
                                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                                onClick = { negativeButtonClickAction?.invoke() }) {
                                Text(
                                    text = content.negativeButtonText,
                                    fontSize = 12.sp,
                                    color = content.mainColor
                                )
                            }
                        }
                        Button(
                            modifier = Modifier.width(100.dp),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = content.mainColor),
                            onClick = { positiveButtonClickAction?.invoke() }) {
                            Text(text = content.positiveButtonText ?: "", fontSize = 12.sp)
                        }
                    }
                }
            }
        })
    }
}

data class JWDialogBoxModel(
    val mainColor: Color,
    val title: String,
    val description: String,
    val positiveButtonText: String?,
    val negativeButtonText: String?
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun JWDialogBoxPreview() {
    BasicAlertDialog(onDismissRequest = { /*TODO*/ }, content = {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(Color.White)
                .wrapContentHeight()
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(bottom = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .height(100.dp)
                        .fillMaxWidth()
                        .background(Red),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        modifier = Modifier.height(50.dp),
                        painter = painterResource(id = R.drawable.attention),
                        contentDescription = ""
                    )
                }
                Text(
                    text = "JWDialog",
                    modifier = Modifier.padding(top = 20.dp),
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font(R.font.tt_bold)),
                    color = Red
                )
                Text(
                    text = "Lorem Ipsum has been the industry's standard dummy text",
                    modifier = Modifier.padding(top = 20.dp, start = 10.dp, end = 10.dp),
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.tt_regular)),
                    textAlign = TextAlign.Center,
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .padding(top = 20.dp, start = 45.dp, end = 45.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        modifier = Modifier
                            .width(100.dp),
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(width = 1.dp, color = Red),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                        onClick = { /*TODO*/ }) {
                        Text(text = "Vazgeç", fontSize = 12.sp, color = Red)
                    }

                    Button(
                        modifier = Modifier.width(100.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Red),
                        onClick = { /*TODO*/ }) {
                        Text(text = "Tamam", fontSize = 12.sp)
                    }
                }
            }
        }
    })
}