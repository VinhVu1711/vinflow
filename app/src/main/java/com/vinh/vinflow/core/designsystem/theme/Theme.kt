package com.vinh.vinflow.core.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkScheme = darkColorScheme(
    primary = SignalOrange,
    onPrimary = Color.White,
    secondary = NavGold,
    onSecondary = Color.White,
    background = CarbonNavy,
    onBackground = Color.White,
    surface = ChromeIndigo,
    onSurface = Color.White,
    surfaceVariant = PeriwinkleMetallic,
    onSurfaceVariant = Color.White,
    error = NintendoRed
)

private val LightScheme = lightColorScheme(
    primary = SignalOrange,
    onPrimary = Color.White,
    secondary = NavGold,
    onSecondary = Color.White,
    background = PaleSky,
    onBackground = PanelInk,
    surface = LightPeriwinkle,
    onSurface = PanelInk,
    surfaceVariant = Lavender,
    onSurfaceVariant = PanelInk,
    error = NintendoRed
)

@Composable
fun VinflowTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkScheme else LightScheme,
        typography = VinflowTypography,
        content = content
    )
}

