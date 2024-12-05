package com.akcay.justwatch.internal.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.akcay.justwatch.R

@Composable
fun SettingsSwitchItem(
  modifier: Modifier = Modifier,
  title: String,
  desc: String,
  checked: Boolean,
  onCheckedChange: (Boolean) -> Unit
) {
  Card(
    modifier = modifier
      .fillMaxWidth()
      .height(72.dp)
      .padding(5.dp),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
  ) {
    Row(
      modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 10.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Image(
        modifier = Modifier
          .clip(CircleShape)
          .size(50.dp),
        painter = painterResource(id = R.drawable.ic_launcher_background),
        contentDescription = null
      )
      Column(
        modifier = Modifier
          .padding(start = 10.dp)
          .weight(1f)
      ) {
        Text(
          text = title,
          style = TextStyle(
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 17.sp, fontFamily = FontFamily(
              Font(
                resId = R.font.tt_medium
              )
            )
          ),
          maxLines = 2,
          overflow = TextOverflow.Ellipsis
        )
        Text(
          text = desc,
          style = TextStyle(
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 12.sp,
            fontFamily = FontFamily(
              Font(
                resId = R.font.tt_regular
              )
            ),
          ),
          maxLines = 2,
          overflow = TextOverflow.Ellipsis
        )
      }

      JWSwitchButton(
        checked = checked,
        onCheckedChange = onCheckedChange,
      )
    }
  }
}


@Composable
@Preview
fun SettingsSwitchItemPreview() {
  Box(modifier = Modifier.fillMaxSize().background(color = Color.White)) {
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .height(72.dp)
        .padding(5.dp),
      colors = CardDefaults.cardColors(containerColor = Color.Magenta)
    ) {
      Row(
        modifier = Modifier
          .fillMaxSize()
          .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Image(
          modifier = Modifier
            .clip(CircleShape)
            .size(50.dp),
          painter = painterResource(id = R.drawable.ic_launcher_background),
          contentDescription = null
        )

        Column(
          modifier = Modifier
            .padding(start = 10.dp)
            .weight(1f)
        ) {
          Text(
            text = "Dark Theme",
            style = TextStyle(
              color = MaterialTheme.colorScheme.onSurface,
              fontSize = 17.sp, fontFamily = FontFamily(
                Font(
                  resId = R.font.tt_medium
                )
              )
            ),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
          )
          Text(
            text = "Try new look on dark version",
            style = TextStyle(
              color = MaterialTheme.colorScheme.onSurface,
              fontSize = 12.sp, fontFamily = FontFamily(
                Font(
                  resId = R.font.tt_regular
                )
              )
            ),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
          )
        }

        JWSwitchButton(
          checked = true,
          onCheckedChange = {}
        )
      }
    }
  }
}