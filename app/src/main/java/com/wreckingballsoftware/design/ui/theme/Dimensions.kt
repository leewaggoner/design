package com.wreckingballsoftware.design.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimensions(
    val SpaceLarge: Dp = 32.dp,
    val Space: Dp = 16.dp,
    val SpaceSmall: Dp = 8.dp,

    val BorderStroke: Dp = 1.dp,
    val ButtonWidth: Dp = 160.dp,
    val CardElevation: Dp = 4.dp,
)

val CustomDimensions = Dimensions()

val LocalDimensions = staticCompositionLocalOf { Dimensions() }

val MaterialTheme.dimensions
    @Composable
    @ReadOnlyComposable
    get() = LocalDimensions.current
