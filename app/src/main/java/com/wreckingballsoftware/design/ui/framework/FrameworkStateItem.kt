package com.wreckingballsoftware.design.ui.framework

import androidx.compose.runtime.Composable

sealed class FrameworkStateItem(
    val isTopBarActionAvailable: Boolean,
    val isNavBarVisible: Boolean,
    val fabAction: @Composable () -> Unit,
) {
    class AuthFrameworkStateItem : FrameworkStateItem(
        isTopBarActionAvailable = false,
        isNavBarVisible = false,
        fabAction = { },
    )
    class CampaignsFrameworkStateItem(fab: @Composable () -> Unit): FrameworkStateItem(
        isTopBarActionAvailable = true,
        isNavBarVisible = true,
        fabAction = fab,
    )
    class CampaignDetailsStateItem : FrameworkStateItem(
        isTopBarActionAvailable = true,
        isNavBarVisible = true,
        fabAction = { }
    )
    class MapFrameworkStateItem(fab: @Composable () -> Unit) : FrameworkStateItem(
        isTopBarActionAvailable = true,
        isNavBarVisible = true,
        fabAction = fab,
    )
    class SignsFrameworkStateItem : FrameworkStateItem(
        isTopBarActionAvailable = true,
        isNavBarVisible = true,
        fabAction = { },
    )
}