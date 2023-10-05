package com.wreckingballsoftware.design.ui.compose

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.wreckingballsoftware.design.R

@Composable
fun DeSignErrorAlert(
    message: String,
    onDismissAlert: () -> Unit,
) {
    AlertDialog(
        title = { Text(text = stringResource(id = R.string.error)) },
        text = { Text(text = message) },
        onDismissRequest = { onDismissAlert() },
        confirmButton = {
            Button(
                onClick = { onDismissAlert() }
            ) {
                Text(text = stringResource(id = R.string.ok))
            }
        }
    )
}