package com.wreckingballsoftware.design.ui.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.wreckingballsoftware.design.R
import com.wreckingballsoftware.design.ui.theme.customColorsPalette

@Composable
fun DeSignFab(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        FloatingActionButton(
            modifier = modifier.then(
                Modifier
                    .align(alignment = Alignment.BottomEnd),
            ),
            onClick = onClick,
            containerColor = MaterialTheme.customColorsPalette.tertiary
        ) {
            Icon(
                imageVector = Icons.Outlined.Add,
                contentDescription = stringResource(id = R.string.create_campaign),
            )
        }
    }
}

@Preview(name = "DeSignFab Preview")
@Composable
fun DeSignFabPreview() {
    DeSignFab { }
}