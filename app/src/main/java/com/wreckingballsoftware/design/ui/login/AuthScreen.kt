package com.wreckingballsoftware.design.ui.login

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.wreckingballsoftware.design.Actions
import com.wreckingballsoftware.design.R
import com.wreckingballsoftware.design.ui.compose.DeSignErrorAlert
import com.wreckingballsoftware.design.ui.compose.GoogleAuthButton
import com.wreckingballsoftware.design.ui.login.models.AuthScreenState
import org.koin.androidx.compose.koinViewModel

@Composable
fun AuthScreen(
    actions: Actions,
    viewModel: AuthViewModel = koinViewModel()
) {
    AuthScreenContent(
        state = viewModel.state,
        startSignIn = viewModel::startSignIn,
        getContract = viewModel::getContract,
        handleAuthResult = viewModel::handleAuthResult,
        signOut = viewModel::signOut,
        onDismissAlert = viewModel::onDismissAlert,
    )
}

@Composable
fun AuthScreenContent(
    state: AuthScreenState,
    startSignIn: () -> Unit,
    getContract: () -> ActivityResultContract<Int, Task<GoogleSignInAccount>?>,
    handleAuthResult: (Task<GoogleSignInAccount>?, String) -> Unit,
    signOut: () -> Unit,
    onDismissAlert: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val signInFailedMessage = stringResource(id = R.string.sign_in_failed)
        val authResultsLauncher = rememberLauncherForActivityResult(
            contract = getContract()
        ) { task ->
            handleAuthResult(task, signInFailedMessage)
        }

        Text(
            text = stringResource(id = R.string.sign_in_with_google)
        )

        GoogleAuthButton(
            text = stringResource(id = state.buttonTextId),
            isLoading = state.isLoading,
        ) {
            if (state.isSignedIn) {
                signOut()
            } else {
                startSignIn()
                authResultsLauncher.launch(1)
            }
        }


        if (state.errorMessage.isNotEmpty()) {
            DeSignErrorAlert(
                message = state.errorMessage,
                onDismissAlert = onDismissAlert,
            )
        }
    }
}
