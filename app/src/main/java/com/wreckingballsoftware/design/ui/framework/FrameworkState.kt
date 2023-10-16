package com.wreckingballsoftware.design.ui.framework

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.wreckingballsoftware.design.ui.navigation.Destinations

/**
 * When a new screen is added, a new FrameworkStateItem must be created and added to getScreen()
 * or there will be no bottom nav bar or top bar action in the framework
 */
@Stable
class FrameworkState(
    private val navController: NavController,
    private val campaignsFrameworkStateItem: FrameworkStateItem.CampaignsFrameworkStateItem
) {
    private val currentScreenRoute: String?
        @Composable get() = navController
            .currentBackStackEntryAsState()
            .value?.destination?.route
    private val currentFrameworkStateItem: FrameworkStateItem?
        @Composable get() = getScreen(currentScreenRoute)
    val isNavBarVisible: Boolean
        @Composable get() = currentFrameworkStateItem?.isNavBarVisible == true
    val isTopBarActionAvailable: Boolean
        @Composable get() = currentFrameworkStateItem?.isTopBarActionAvailable == true
    val fabAction: @Composable (() -> Unit)?
        @Composable get() = currentFrameworkStateItem?.fabAction

    private fun getScreen(route: String?): FrameworkStateItem? =
        when (route) {
            Destinations.AuthScreen -> FrameworkStateItem.AuthFrameworkStateItem()
            Destinations.CampaignsScreen -> campaignsFrameworkStateItem
            Destinations.CampaignDetailsScreen -> FrameworkStateItem.CampaignDetailsStateItem()
            Destinations.MapScreen -> FrameworkStateItem.MapFrameworkStateItem()
            Destinations.SignsScreen -> FrameworkStateItem.SignsFrameworkStateItem()
            else -> null
        }
}

@Composable
fun rememberFrameworkState(
    navController: NavController,
    campaignsFrameworkStateItem: FrameworkStateItem.CampaignsFrameworkStateItem
) = remember {
    FrameworkState(
        navController,
        campaignsFrameworkStateItem,
    )
}