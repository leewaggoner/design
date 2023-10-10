package com.wreckingballsoftware.design.ui.framework

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.ui.graphics.vector.ImageVector
import com.wreckingballsoftware.design.R
import com.wreckingballsoftware.design.ui.navigation.Actions

sealed class NavBarItem(
    val titleId: Int = 0,
    val icon: ImageVector = Icons.Outlined.Home,
    val action: () -> Unit = { },
) {
    class CampaignsNavItem(actions: Actions): NavBarItem(
        titleId = R.string.campaigns,
        icon = Icons.Outlined.Home,
        action = actions.navigateToCampaignsScreen,
    )
    class MapNavItem(actions: Actions): NavBarItem(
        titleId = R.string.map,
        icon = Icons.Outlined.LocationOn,
        action = actions.navigateToMapScreen,
    )
    class SignsNavItem(actions: Actions): NavBarItem(
        titleId = R.string.signs,
        icon = Icons.Outlined.List,
        action = actions.navigateToSignsScreen,
    )
}