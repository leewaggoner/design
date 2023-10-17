package com.wreckingballsoftware.design.ui.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.wreckingballsoftware.design.R

@Composable
fun DeSignErrorAlert(
    message: String,
    onDismissAlert: () -> Unit,
) {
    DeSignAlert(
        title = stringResource(id = R.string.error),
        message = message,
        onDismissRequest = onDismissAlert,
        onConfirmAlert = onDismissAlert,
        onDismissAlert = null,
    )
}
