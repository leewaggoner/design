package com.wreckingballsoftware.design.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.ui.graphics.vector.ImageVector
import com.wreckingballsoftware.design.Actions
import com.wreckingballsoftware.design.Destinations
import com.wreckingballsoftware.design.R

sealed class Screen(
    val titleId: Int,
    val route: String,
    val isTopBarActionAvailable: Boolean,
    val isNavBarVisible: Boolean,
    val icon: ImageVector,
    val action: () -> Unit,
) {
    class AuthScreen() : Screen(
        titleId = 0,
        route = Destinations.AuthScreen,
        isTopBarActionAvailable = false,
        isNavBarVisible = false,
        icon = Icons.Outlined.Add,
        action = { },
    )
    class CampaignsScreen(actions: Actions): Screen(
        titleId = R.string.campaigns,
        route = Destinations.CampaignsScreen,
        isTopBarActionAvailable = true,
        isNavBarVisible = true,
        icon = Icons.Outlined.Home,
        action = actions.navigateToCampaignsScreen,
    )
    class MapScreen(actions: Actions): Screen(
        titleId = R.string.map,
        route = Destinations.MapScreen,
        isTopBarActionAvailable = true,
        isNavBarVisible = true,
        icon = Icons.Outlined.LocationOn,
        action = actions.navigateToMapScreen,
    )
    class SignsScreen(actions: Actions): Screen(
        titleId = R.string.signs,
        route = Destinations.SignsScreen,
        isTopBarActionAvailable = true,
        isNavBarVisible = true,
        icon = Icons.Outlined.List,
        action = actions.navigateToSignsScreen,
    )
}