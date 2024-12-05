package com.akcay.justwatch.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
//  primary = Purple80,
//  secondary = PurpleGrey80,
//  tertiary = Pink80
  primary = Color(0xFFB71C1C),      // Koyu Kırmızı (Sinema teması)
  secondary = Color(0xFFFFC107),    // Parlak Sarı (Öne çıkan alanlar için)
  tertiary = Color(0xFF0288D1),     // Koyu Mavi (Ek aksanlar)
  background = Color(0xFF121212),   // Siyah (Genel arka plan)
  surface = Color(0xFF1E1E1E),      // Koyu Gri (Kart arka planları)
  onPrimary = Color(0xFFFFFFFF),    // Beyaz (Primary renk üzerindeki yazılar için)
  onSecondary = Color(0xFF000000),  // Siyah (Secondary renk üzerindeki yazılar için)
  onBackground = Color(0xFFFFFFFF), // Beyaz (Genel yazılar için)
  onSurface = Color(0xFFFFFFFF)     // Beyaz (Surface üzerindeki yazılar için)
)

private val LightColorScheme = lightColorScheme(
//  primary = Purple40,
//  secondary = PurpleGrey40,
//  tertiary = Pink40
  primary = Color(0xFFD32F2F),      // Kırmızı (Ana Renk - Duygu yaratır)
  secondary = Color(0xFFFFA000),    // Altın Sarısı (Odak noktalarını vurgular)
  tertiary = Color(0xFF1976D2),     // Mavi (Soğuk ama modern bir ton)
  background = Color(0xFFFFFFFF),   // Beyaz (Temiz bir görünüm)
  surface = Color(0xFFF7F7F7),      // Açık Gri (Kutu arka planları için)
  onPrimary = Color(0xFFFFFFFF),    // Beyaz (Primary renk üzerindeki yazılar için)
  onSecondary = Color(0xFFFFFFFF),  // Beyaz (Secondary renk üzerindeki yazılar için)
  onBackground = Color(0xFF000000), // Siyah (Genel yazılar için)
  onSurface = Color(0xFF000000)     // Siyah (Surface üzerindeki yazılar için)

  /* Other default colors to override
  background = Color(0xFFFFFBFE),
  surface = Color(0xFFFFFBFE),
  onPrimary = Color.White,
  onSecondary = Color.White,
  onTertiary = Color.White,
  onBackground = Color(0xFF1C1B1F),
  onSurface = Color(0xFF1C1B1F),
  */
)

@Composable
fun JustWatchTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  content: @Composable () -> Unit
) {
  val colorScheme = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
      val context = LocalContext.current
      if (darkTheme) {
        dynamicDarkColorScheme(context).copy(
          primary = DarkColorScheme.primary,
          secondary = DarkColorScheme.secondary,
          tertiary = DarkColorScheme.tertiary,
          background = DarkColorScheme.background,
          surface = DarkColorScheme.surface,
          onPrimary = DarkColorScheme.onPrimary,
          onSecondary = DarkColorScheme.onSecondary,
          onBackground = DarkColorScheme.onBackground,
          onSurface = DarkColorScheme.onSurface
        )
      } else {
        dynamicLightColorScheme(context).copy(
          primary = LightColorScheme.primary,
          secondary = LightColorScheme.secondary,
          tertiary = LightColorScheme.tertiary,
          background = LightColorScheme.background,
          surface = LightColorScheme.surface,
          onPrimary = LightColorScheme.onPrimary,
          onSecondary = LightColorScheme.onSecondary,
          onBackground = LightColorScheme.onBackground,
          onSurface = LightColorScheme.onSurface
        )
      }
    }

    darkTheme -> DarkColorScheme
    else -> LightColorScheme
  }

  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    content = content
  )
}