package com.wreckingballsoftware.design.ui.compose

import android.Manifest
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.wreckingballsoftware.design.R


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CheckPermissions(
    content: @Composable () -> Unit
) {
    val permissions = listOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
    )
    val permissionState = rememberMultiplePermissionsState(
        permissions = permissions
    )

    if (permissionState.allPermissionsGranted) {
        content()
    } else {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            DeSignAlert(
                title = stringResource(id = R.string.grant_permissions),
                message = if (permissionState.shouldShowRationale) {
                    stringResource(id = R.string.permission_rationale)
                } else {
                    stringResource(id = R.string.permission_text)
                },
                onDismissRequest = { },
                onConfirmAlert = { permissionState.launchMultiplePermissionRequest() },
                onDismissAlert = null,
            )
        }
    }
}