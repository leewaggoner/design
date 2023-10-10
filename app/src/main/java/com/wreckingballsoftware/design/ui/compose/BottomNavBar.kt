package com.wreckingballsoftware.design.ui.compose

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.wreckingballsoftware.design.ui.framework.NavBarItem

@Composable
fun BottomNavBar(
    navBarItems: List<NavBarItem>,
) {
    NavigationBar() {
        navBarItems.forEach { item ->
            val title = stringResource(id = item.titleId)
            NavigationBarItem(
                label = { Text(text = title) },
                selected = false,
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