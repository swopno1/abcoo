package com.example.ui.theme

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

private val PlayfulLightColorScheme = lightColorScheme(
    primary = KidBlue,
    onPrimary = Color.White,
    primaryContainer = KidBlueLight,
    onPrimaryContainer = Color(0xFF00325B),
    secondary = KidOrange,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFFFE0B2),
    onSecondaryContainer = Color(0xFF5D2800),
    tertiary = KidGreen,
    onTertiary = Color.White,
    background = KidBackground,
    onBackground = KidTextPrimary,
    surface = KidSurface,
    onSurface = KidTextPrimary,
    surfaceVariant = Color(0xFFF1F5F9),
    onSurfaceVariant = KidTextSecondary
)

private val PlayfulDarkColorScheme = darkColorScheme(
    primary = Color(0xFF90CAF9),
    onPrimary = Color(0xFF00325B),
    secondary = Color(0xFFFFB74D),
    onSecondary = Color(0xFF5D2800),
    tertiary = Color(0xFFA5D6A7),
    background = Color(0xFF0F172A),
    surface = Color(0xFF1E293B)
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Keep consistent cheerful branding across devices
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) PlayfulDarkColorScheme else PlayfulLightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
