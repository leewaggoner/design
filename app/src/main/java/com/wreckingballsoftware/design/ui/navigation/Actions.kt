package com.wreckingballsoftware.design.ui.navigation

import androidx.navigation.NavController

class Actions(navController: NavController) {
    val navigateUp: () -> Unit = {
        navController.navigateUp()
    }
    val navigateToAuthScreen: () -> Unit = {
        navController.navigate(Destinations.AuthScreen) {
            //clear the whole backstack
            popUpTo(navController.graph.id) {
                inclusive = true
            }
        }
    }
    val navigateToCampaignsScreen: () -> Unit = {
        navController.navigate(Destinations.CampaignsScreen) {
            //clear the whole backstack
            popUpTo(navController.graph.id) {
                inclusive = true
            }
        }
    }
    val navigateToCampaignDetailsScreen: (Long) -> Unit = { campaignId ->
        navController.navigate(
            Destinations.CampaignDetailsScreen.replace(
                oldValue = "{campaignId}",
                newValue = campaignId.toString()
            )
        )
    }
    val navigateToMapScreen: () -> Unit = {
        navController.navigate(Destinations.MapScreen) {
            //clear the whole backstack
            popUpTo(navController.graph.id) {
                inclusive = true
            }
        }
    }
    val navigateToSignsScreen: () -> Unit = {
        navController.navigate(Destinations.SignsScreen) {
            //clear the whole backstack
            popUpTo(navController.graph.id) {
                inclusive = true
            }
        }
    }
}