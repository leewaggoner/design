package com.wreckingballsoftware.design

import androidx.navigation.NavController

object Destinations {
    const val AuthScreen = "AuthScreen"
    const val MainScreen = "MainScreen"
}

class Actions(navController: NavController) {
    val navigateToMainScreen: () -> Unit = {
        navController.navigate(
            Destinations.MainScreen
        ) {
            popUpTo(navController.graph.id) {
                inclusive = true
            }
        }
    }
}