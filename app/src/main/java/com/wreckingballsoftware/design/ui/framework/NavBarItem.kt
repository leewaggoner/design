package com.wreckingballsoftware.design.ui.framework

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.ui.graphics.vector.ImageVector
import com.wreckingballsoftware.design.R
import com.wreckingballsoftware.design.ui.navigation.Actions
import com.wreckingballsoftware.design.ui.navigation.Destinations

sealed class NavBarItem(
    val titleId: Int = 0,
    val route: String = "",
    val icon: ImageVector = Icons.Outlined.Home,
    val action: () -> Unit = { },
) {
    class CampaignsNavItem(actions: Actions): NavBarItem(
        titleId = R.string.campaigns,
        route = Destinations.CampaignsGraph,
        icon = Icons.Outlined.Home,
        action = actions.navigateToCampaignsGraph,
    )
    class MapNavItem(actions: Actions): NavBarItem(
        titleId = R.string.map,
        route = Destinations.MapGraph,
        icon = Icons.Outlined.LocationOn,
        action = actions.navigateToMapGraph,
    )
    class SignsNavItem(actions: Actions): NavBarItem(
        titleId = R.string.signs,
        route = Destinations.SignsGraph,
        icon = Icons.Outlined.List,
        action = actions.navigateToSignsGraph,
    )
}