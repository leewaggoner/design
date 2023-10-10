package com.wreckingballsoftware.design.ui.compose

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.wreckingballsoftware.design.R
import com.wreckingballsoftware.design.domain.GoogleAuth
import com.wreckingballsoftware.design.ui.navigation.Actions
import org.koin.compose.koinInject

@Composable
fun TopBarAction(actions: Actions, googleAuth: GoogleAuth = koinInject()) {
    var menuExpanded by remember {
        mutableStateOf(false)
    }

    IconButton(onClick = { menuExpanded = !menuExpanded }) {
        Icon(
            imageVector = Icons.Outlined.MoreVert,
            contentDescription = stringResource(id = R.string.open_menu),
            tint = Color.White,
        )
    }
    DropdownMenu(
        expanded = menuExpanded,
        onDismissRequest = { menuExpanded = false },
    ) {
        DropdownMenuItem(
            text = {
                Text(text = stringResource(id = R.string.sign_out))
            },
            onClick = {
                googleAuth.signOut { actions.navigateToAuthScreen() }
            },
        )
    }
}