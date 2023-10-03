package com.wreckingballsoftware.design

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.wreckingballsoftware.design.ui.MainScreen
import com.wreckingballsoftware.design.ui.login.AuthScreen

@Composable
fun DeSignHost() {
    val navController = rememberNavController()
    val actions = remember(navController) { Actions(navController) }

    val startDestination = Destinations.AuthScreen

    NavHost(navController = navController, startDestination = startDestination) {
        composable(Destinations.AuthScreen) {
            AuthScreen(actions = actions)
        }

        composable(Destinations.MainScreen) {
            MainScreen(actions = actions)
        }
    }
}
