package com.wreckingballsoftware.design.ui.compose

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.wreckingballsoftware.design.ui.framework.NavBarItem

@Composable
fun BottomNavBar(
    navController: NavController,
    navBarItems: List<NavBarItem>,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    NavigationBar {
        navBarItems.forEach { item ->
            val title = stringResource(id = item.titleId)
            NavigationBarItem(
                label = { Text(text = title) },
                selected = currentDestination?.hierarchy?.any {
                    it.route == item.route
                } == true,
                onClick = { item.action() },
                icon = { 
                    Icon(
                        imageVector = item.icon,
                        contentDescription = title,
                    )
                },
            )
        }
    }
}