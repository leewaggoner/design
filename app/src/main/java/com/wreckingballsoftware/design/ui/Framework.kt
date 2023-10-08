package com.wreckingballsoftware.design.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.wreckingballsoftware.design.Actions
import com.wreckingballsoftware.design.R
import com.wreckingballsoftware.design.domain.GoogleAuth
import com.wreckingballsoftware.design.rememberNavBarState
import com.wreckingballsoftware.design.ui.campaigns.CampaignsScreen
import com.wreckingballsoftware.design.ui.compose.BottomBar
import com.wreckingballsoftware.design.ui.compose.TopBar
import com.wreckingballsoftware.design.ui.login.AuthScreen
import com.wreckingballsoftware.design.ui.map.MapScreen
import com.wreckingballsoftware.design.ui.signs.SignsScreen
import com.wreckingballsoftware.design.ui.theme.customColorsPalette
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Framework(googleAuth: GoogleAuth = koinInject()) {
    val navController = rememberNavController()
    val actions = remember(navController) { Actions(navController) }
    val authScreen = Screen.AuthScreen()
    val campaignsScreen = Screen.CampaignsScreen(actions = actions)
    val mapScreen = Screen.MapScreen(actions = actions)
    val signsScreen = Screen.SignsScreen(actions = actions)
    val startDestination = authScreen.route
    val screens = listOf(
        authScreen,
        campaignsScreen,
        mapScreen,
        signsScreen,
    )
    val navItems = screens.subList(1, screens.size)
    val navBarState = rememberNavBarState(
        navController,
    )

    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(id = R.string.app_name),
                topBarAction = {
                    if (navBarState.isTopBarActionAvailable) {
                        IconButton(
                            onClick = {
                                googleAuth.signOut {
                                    actions.navigateToAuthScreen()
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.AccountCircle,
                                contentDescription = stringResource(id = R.string.sign_out),
                                tint = Color.White,
                            )
                        }
                    }
                }
            )
        },
        bottomBar = {
            if (navBarState.isVisible) {
                BottomBar(
                    screens = navItems
                )
            }
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(MaterialTheme.customColorsPalette.background),
        ) {
            NavHost(navController = navController, startDestination = startDestination) {
                composable(authScreen.route) {
                    AuthScreen(actions = actions)
                }
                composable(campaignsScreen.route) {
                    CampaignsScreen(actions = actions)
                }
                composable(mapScreen.route) {
                    MapScreen(actions = actions)
                }
                composable(signsScreen.route) {
                    SignsScreen(actions = actions)
                }
            }
        }
    }

}