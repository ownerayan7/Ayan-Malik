package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = RosePrimary,
    onPrimary = Color.White,
    primaryContainer = MistyRose,
    onPrimaryContainer = DeepPurple,
    secondary = RoseDark,
    onSecondary = Color.White,
    secondaryContainer = LavenderAccent,
    onSecondaryContainer = DeepPurple,
    background = SoftPinkBackground,
    onBackground = TextPrimary,
    surface = CreamWhite,
    onSurface = TextPrimary,
    surfaceVariant = Color(0xFFFFF5F7),
    onSurfaceVariant = TextSecondary
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    // We intentionally keep the gentle pastel romantic palette consistent
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}
