package com.wreckingballsoftware.design.ui.compose

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun BottomBar(screens: List<String>) {
    NavigationBar() {
        screens.forEach { screenName ->
            NavigationBarItem(
                label = { Text(text = screenName) },
                selected = false,
                onClick = { /*TODO*/ },
                icon = { 
                    Icon(
                        imageVector = Icons.Outlined.Home,
                        contentDescription = screenName,
                    )
                },
            )
        }
    }
}