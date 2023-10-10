package com.wreckingballsoftware.design.ui.framework

sealed class FrameworkStateItem(
    val isTopBarActionAvailable: Boolean,
    val isNavBarVisible: Boolean,
) {
    data object AuthFrameworkStateItem: FrameworkStateItem(
        isTopBarActionAvailable = false,
        isNavBarVisible = false,
    )
    data object CampaignsFrameworkStateItem: FrameworkStateItem(
        isTopBarActionAvailable = true,
        isNavBarVisible = true,
    )
    data object MapFrameworkStateItem: FrameworkStateItem(
        isTopBarActionAvailable = true,
        isNavBarVisible = true,
    )
    data object SignsFrameworkStateItem: FrameworkStateItem(
        isTopBarActionAvailable = true,
        isNavBarVisible = true,
    )
}