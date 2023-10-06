package com.wreckingballsoftware.design.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimensions(
    val ButtonWidth: Dp = 200.dp,
    val SpaceBig: Dp = 32.dp,
    val SpaceMedium: Dp = 16.dp,
    val SpaceSmall: Dp = 8.dp,
)

val CustomDimensions = Dimensions()

val LocalDimensions = staticCompositionLocalOf { Dimensions() }

val MaterialTheme.dimensions
    @Composable
    @ReadOnlyComposable
    get() = LocalDimensions.current
