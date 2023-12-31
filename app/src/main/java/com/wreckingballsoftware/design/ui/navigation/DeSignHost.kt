package com.wreckingballsoftware.design.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.wreckingballsoftware.design.domain.GoogleAuth
import com.wreckingballsoftware.design.ui.campaigns.CampaignsScreen
import com.wreckingballsoftware.design.ui.compose.CheckPermissions
import com.wreckingballsoftware.design.ui.details.CampaignDetailsScreen
import com.wreckingballsoftware.design.ui.login.AuthScreen
import com.wreckingballsoftware.design.ui.map.MapScreen
import com.wreckingballsoftware.design.ui.signs.SignsScreen
import org.koin.compose.koinInject

@Composable
fun DeSignHost(
    navController: NavHostController,
    actions: Actions,
    googleAuth: GoogleAuth = koinInject(),
) {
    var startDestination = Destinations.AuthScreen
    if (googleAuth.isSignedIn()) {
        startDestination = Destinations.CampaignsGraph
    }

    NavHost(navController = navController, startDestination = startDestination) {
        composable(Destinations.AuthScreen) {
            AuthScreen(actions = actions)
        }
        campaignsGraph(actions = actions)
        mapGraph()
        signsGraph(actions = actions)
    }
}

fun NavGraphBuilder.campaignsGraph(actions: Actions) {
    navigation(
        route = Destinations.CampaignsGraph,
        startDestination = Destinations.CampaignsScreen
    ) {
        composable(Destinations.CampaignsScreen) {
            CampaignsScreen(actions = actions)
        }

        composable(
            route = Destinations.CampaignDetailsScreen,
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
    }
}

fun NavGraphBuilder.mapGraph() {
    navigation(
        route = Destinations.MapGraph,
        startDestination = Destinations.MapScreen
    ) {
        composable(
            route = Destinations.MapScreen,
            arguments = listOf(
                navArgument("campaignId") { type = NavType.LongType },
                navArgument("signId") { type = NavType.LongType }
            )
        ) {backStackEntry ->
            val campaignId = backStackEntry.arguments?.getLong("campaignId")
            val signId = backStackEntry.arguments?.getLong("signId")
            campaignId?.let { id ->
                CheckPermissions {
                    MapScreen(campaignId = id, signId = signId ?: 0L)
                }
            }
        }
    }
}

fun NavGraphBuilder.signsGraph(actions: Actions) {
    navigation(
        route = Destinations.SignsGraph,
        startDestination = Destinations.SignsScreen
    ) {
        composable(
            route = Destinations.SignsScreen,
            arguments = listOf(navArgument("campaignId") { type = NavType.LongType })
        ) { backStackEntry ->
            val campaignId = backStackEntry.arguments?.getLong("campaignId")
            campaignId?.let { id ->
                SignsScreen(actions = actions, campaignId = id)
            }
        }
    }
}
