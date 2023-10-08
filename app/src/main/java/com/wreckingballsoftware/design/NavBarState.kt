package com.wreckingballsoftware.design

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.wreckingballsoftware.design.ui.Screen

@Stable
class NavBarState(
    private val navController: NavController,
) {
    private val currentScreenRoute: String?
        @Composable get() = navController
            .currentBackStackEntryAsState()
            .value?.destination?.route
    val currentScreen: Screen?
        @Composable get() = getScreen(currentScreenRoute)
    val isVisible: Boolean
        @Composable get() = currentScreen?.isNavBarVisible == true
    val isTopBarActionAvailable: Boolean
        @Composable get() = currentScreen?.isTopBarActionAvailable == true
    val icon: ImageVector?
        @Composable get() = currentScreen?.icon
    val iconContentDescription: String
        @Composable get() = stringResource(id = currentScreen?.titleId ?: R.string.error)
    val onIconClick: (() -> Unit)?
        @Composable get() = currentScreen?.action
    val title: String
        @Composable get() = stringResource(id = currentScreen?.titleId ?: R.string.error)

    private fun getScreen(route: String?): Screen? =
        when (route) {
            Destinations.AuthScreen -> Screen.AuthScreen()
            Destinations.CampaignsScreen -> Screen.CampaignsScreen(Actions(navController))
            Destinations.MapScreen -> Screen.MapScreen(Actions(navController))
            Destinations.SignsScreen -> Screen.SignsScreen(Actions(navController))
            else -> null
        }
}

@Composable
fun rememberNavBarState(
    navController: NavController,
) = remember {
    NavBarState(
        navController,
    )
}