package com.wreckingballsoftware.design

import androidx.navigation.NavController

object Destinations {
    const val AuthScreen = "AuthScreen"
    const val CampaignsScreen = "Campaigns"
    const val MapScreen = "Map"
    const val SignsScreen = "Signs"
}

class Actions(navController: NavController) {
    val navigateToCampaignsScreen: () -> Unit = {
        navController.navigate(Destinations.CampaignsScreen)
    }
    val navigateToMapScreen: () -> Unit = {
        navController.navigate(Destinations.MapScreen)
    }
    val navigateToSignsScreen: () -> Unit = {
        navController.navigate(Destinations.SignsScreen)
    }
}