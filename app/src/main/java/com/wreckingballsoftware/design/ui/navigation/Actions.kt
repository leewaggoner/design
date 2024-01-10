package com.wreckingballsoftware.design.ui.navigation

import androidx.lifecycle.LifecycleCoroutineScope
import androidx.navigation.NavController
import com.wreckingballsoftware.design.database.INVALID_SIGN_MARKER_ID
import com.wreckingballsoftware.design.repos.UserRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Actions(navController: NavController, scope: LifecycleCoroutineScope, userRepo: UserRepo) {
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
    val navigateToCampaignsGraph: () -> Unit = {
        navController.navigate(Destinations.CampaignsGraph) {
            //clear the whole backstack
            popUpTo(navController.graph.id) {
                inclusive = true
            }
            launchSingleTop = true
        }
    }
    val navigateToMapGraph: () -> Unit = {
        scope.launch(Dispatchers.Main) {
            val campaignId = userRepo.getSelectedCampaignId()
            val destination = Destinations.MapScreen.replace(
                oldValue = "{signId}",
                newValue = INVALID_SIGN_MARKER_ID.toString()
            )
            navController.navigate(
                destination.replace(
                    oldValue = "{campaignId}",
                    newValue = campaignId.toString()
                )
            ) {
                //clear the whole backstack
                popUpTo(navController.graph.id) {
                    inclusive = true
                }
                launchSingleTop = true
            }
        }
    }
    val navigateToSignsGraph: () -> Unit = {
        scope.launch(Dispatchers.Main) {
            val campaignId = userRepo.getSelectedCampaignId()
            navController.navigate(
                Destinations.SignsScreen.replace(
                    oldValue = "{campaignId}",
                    newValue = campaignId.toString()
                )
            ) {
                //clear the whole backstack
                popUpTo(navController.graph.id) {
                    inclusive = true
                }
                launchSingleTop = true
            }
        }
    }
    val navigateToCampaignsScreen: () -> Unit = {
        navController.navigate(Destinations.CampaignsScreen) {
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
    val navigateToMapScreen: (Long?) -> Unit = { signId ->
        scope.launch(Dispatchers.Main) {
            val campaignId = userRepo.getSelectedCampaignId()
            val destination = Destinations.MapScreen.replace(
                oldValue = "{signId}",
                newValue = signId?.toString() ?: INVALID_SIGN_MARKER_ID.toString())
            navController.navigate(
                destination.replace(
                    oldValue = "{campaignId}",
                    newValue = campaignId.toString()
                )
            ) {
                //clear the whole backstack
                popUpTo(navController.graph.id) {
                    inclusive = true
                }
                launchSingleTop = true
            }
        }
    }
    val navigateToSignsScreen: () -> Unit = {
        navController.navigate(Destinations.SignsScreen) {
        }
    }
}