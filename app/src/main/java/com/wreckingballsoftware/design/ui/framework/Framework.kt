package com.wreckingballsoftware.design.ui.framework

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.rememberNavController
import com.wreckingballsoftware.design.R
import com.wreckingballsoftware.design.ui.campaigns.getCampaignFrameworkStateItem
import com.wreckingballsoftware.design.ui.compose.BottomNavBar
import com.wreckingballsoftware.design.ui.compose.TopBar
import com.wreckingballsoftware.design.ui.compose.TopBarAction
import com.wreckingballsoftware.design.ui.navigation.Actions
import com.wreckingballsoftware.design.ui.navigation.DeSignHost
import com.wreckingballsoftware.design.ui.theme.customColorsPalette

/**
 * The app container, which includes the top bar and the bottom navigation bar
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Framework() {
    val navController = rememberNavController()
    val actions = remember(navController) { Actions(navController) }
    val frameworkState = rememberFrameworkState(
        navController = navController,
        campaignsFrameworkStateItem = getCampaignFrameworkStateItem()
    )

    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(id = R.string.app_name),
                topBarAction = {
                    if (frameworkState.isTopBarActionAvailable) {
                        TopBarAction(actions = actions)
                    }
                }
            )
        },
        bottomBar = {
            if (frameworkState.isNavBarVisible) {
                BottomNavBar(
                    navBarItems = listOf(
                        NavBarItem.CampaignsNavItem(actions),
                        NavBarItem.MapNavItem(actions),
                        NavBarItem.SignsNavItem(actions),
                    )
                )
            }
        },
        floatingActionButton = frameworkState.fabAction ?: { }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(MaterialTheme.customColorsPalette.background),
        ) {
            DeSignHost(navController = navController, actions = actions)
        }
    }
}
