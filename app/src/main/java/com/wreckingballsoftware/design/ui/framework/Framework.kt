package com.wreckingballsoftware.design.ui.framework

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.navigation.compose.rememberNavController
import com.wreckingballsoftware.design.R
import com.wreckingballsoftware.design.repos.UserRepo
import com.wreckingballsoftware.design.ui.campaigns.getCampaignFrameworkStateItem
import com.wreckingballsoftware.design.ui.compose.BottomNavBar
import com.wreckingballsoftware.design.ui.compose.TopBar
import com.wreckingballsoftware.design.ui.compose.TopBarAction
import com.wreckingballsoftware.design.ui.map.getMapFrameworkStateItem
import com.wreckingballsoftware.design.ui.navigation.Actions
import com.wreckingballsoftware.design.ui.navigation.DeSignHost
import com.wreckingballsoftware.design.ui.theme.customColorsPalette

/**
 * The app container, which includes the top bar and the bottom navigation bar
 */
@Composable
fun Framework(scope: LifecycleCoroutineScope, userRepo: UserRepo, connectionState: Boolean) {
    val navController = rememberNavController()
    val actions = remember(navController) { Actions(navController, scope, userRepo) }
    val frameworkState = rememberFrameworkState(
        navController = navController,
        campaignsFrameworkStateItem = getCampaignFrameworkStateItem(),
        mapFrameworkStateItem = getMapFrameworkStateItem(userRepo),
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
                    navController = navController,
                    navBarItems = listOf(
                        NavBarItem.CampaignsNavItem(actions),
                        NavBarItem.MapNavItem(actions),
                        NavBarItem.SignsNavItem(actions),
                    ),
                )
            }
        },
        floatingActionButton = frameworkState.fabAction ?: { },
        snackbarHost = {
            if (!connectionState) {
                Toast.makeText(LocalContext.current, R.string.network_warning, Toast.LENGTH_SHORT)
                    .show()
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.customColorsPalette.background),
        ) {
            DeSignHost(navController = navController, actions = actions)
        }
    }
}
