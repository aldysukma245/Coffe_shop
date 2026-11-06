package com.example.coffee_shop.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val CoffeeColorScheme = lightColorScheme(
    primary = OrangePrimary,
    onPrimary = Color.White,
    primaryContainer = OrangeLight,
    onPrimaryContainer = OrangePrimary,
    secondary = CoffeeMedium,
    onSecondary = Color.White,
    background = BgLight,
    onBackground = TextDark,
    surface = Color.White,
    onSurface = TextDark,
    surfaceVariant = Color(0xFFF9F6F0),
    onSurfaceVariant = TextMuted
)

@Composable
fun Coffee_shopTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = CoffeeColorScheme,
        typography = Typography,
        content = content
    )
}
