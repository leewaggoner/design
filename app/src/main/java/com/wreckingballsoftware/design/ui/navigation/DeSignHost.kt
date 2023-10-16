package com.wreckingballsoftware.design.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.wreckingballsoftware.design.domain.GoogleAuth
import com.wreckingballsoftware.design.ui.campaigns.CampaignsScreen
import com.wreckingballsoftware.design.ui.details.CampaignDetailsScreen
import com.wreckingballsoftware.design.ui.login.AuthScreen
import com.wreckingballsoftware.design.ui.map.MapScreen
import com.wreckingballsoftware.design.ui.signs.SignsScreen
import org.koin.compose.koinInject

@Composable
fun DeSignHost(
    navController: NavHostController,
    actions: Actions,
    googleAuth: GoogleAuth = koinInject())
{
    var startDestination = Destinations.AuthScreen
    if (googleAuth.isSignedIn()) {
        startDestination = Destinations.CampaignsScreen
    }

    NavHost(navController = navController, startDestination = startDestination) {
        composable(Destinations.AuthScreen) {
            AuthScreen(actions = actions)
        }
        composable(Destinations.CampaignsScreen) {
            CampaignsScreen(actions = actions)
        }
        composable(
            Destinations.CampaignDetailsScreen,
            arguments = listOf(navArgument("campaignId") { type = NavType.LongType })
        ) { backStackEntry ->
            val campaignId = backStackEntry.arguments?.getLong("campaignId")
            campaignId?.let { id ->
                CampaignDetailsScreen(
                    actions,
                    campaignId = id,
                )
            }
        }
        composable(Destinations.MapScreen) {
            MapScreen(actions = actions)
        }
        composable(Destinations.SignsScreen) {
            SignsScreen(actions = actions)
        }
    }
}