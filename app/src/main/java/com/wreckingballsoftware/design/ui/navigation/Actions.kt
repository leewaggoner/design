package com.wreckingballsoftware.design.ui.navigation

import androidx.navigation.NavController

class Actions(navController: NavController) {
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