package com.wreckingballsoftware.design.ui.compose

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.wreckingballsoftware.design.ui.Screen

@Composable
fun BottomBar(
    screens: List<Screen>,
) {
    NavigationBar() {
        screens.forEach { screen ->
            val title = stringResource(id = screen.titleId)
            NavigationBarItem(
                label = { Text(text = title) },
                selected = false,
                onClick = { screen.action() },
                icon = { 
                    Icon(
                        imageVector = screen.icon,
                        contentDescription = title,
                    )
                },
            )
        }
    }
}