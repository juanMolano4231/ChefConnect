package com.example.chefconnect.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val Green = Color(0xFF2E7D32)
private val LightGreen = Color(0xFFA5D6A7)

@Composable
fun ChefTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = Green,
            secondary = LightGreen
        ),
        content = content
    )
}