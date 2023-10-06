package com.wreckingballsoftware.design.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class CustomColorsPalette(
    val primary: Color = Color.Unspecified,
    val secondary: Color = Color.Unspecified,
    val tertiary: Color = Color.Unspecified,
    val background: Color = Color.Unspecified,
)

val LightCustomColorsPalette = CustomColorsPalette(
    primary = AppGreen,
    secondary = AppRed,
    tertiary = AppGold,
    background = AppLightBackgroundGold,
)

val DarkCustomColorsPalette = CustomColorsPalette(
    primary = AppGreen,
    secondary = AppRed,
    tertiary = AppGold,
    background = AppDarkBackgroundGold,
)

val LocalCustomColorsPalette = staticCompositionLocalOf { CustomColorsPalette() }

val MaterialTheme.customColorsPalette: CustomColorsPalette
    @Composable
    @ReadOnlyComposable
    get() = LocalCustomColorsPalette.current
