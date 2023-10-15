package com.wreckingballsoftware.design.ui.compose

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.tooling.preview.Preview
import com.wreckingballsoftware.design.ui.theme.customColorsPalette

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    modifier: Modifier = Modifier,
    topBarAction: @Composable () -> Unit,
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
            )
        },
        actions = { topBarAction() },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.customColorsPalette.primary,
            titleContentColor = White,
        ),
    )
}

@Preview("TopBar Preview")
@Composable
fun TopBarPreview() {
    TopBar(
        title = "My App",
        topBarAction = { },
    )
}
