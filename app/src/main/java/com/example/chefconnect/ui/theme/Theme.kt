package com.example.chefconnect.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// Paleta verde
private val GreenPrimary = Color(0xFF2E7D32)
private val GreenSecondary = Color(0xFF66BB6A)
private val GreenTertiary = Color(0xFFA5D6A7)

private val DarkColorScheme = darkColorScheme(
    primary = GreenSecondary,
    secondary = GreenTertiary,
    tertiary = GreenPrimary,
    background = Color(0xFF0B1F0E),   // verde oscuro
    surface = Color(0xFF1B5E20),      // verde fuerte
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onTertiary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = GreenPrimary,
    secondary = GreenSecondary,
    tertiary = GreenTertiary,
    background = Color(0xFFE8F5E9),   // verde claro
    surface = Color(0xFFC8E6C9),      // verde suave
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onTertiary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black
)

@Composable
fun ChefConnectTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // desactivado para forzar verde
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
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