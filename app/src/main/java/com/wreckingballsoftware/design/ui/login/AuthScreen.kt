package com.wreckingballsoftware.design.ui.login

import android.content.Context
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.wreckingballsoftware.design.R
import com.wreckingballsoftware.design.ui.compose.DeSignErrorAlert
import com.wreckingballsoftware.design.ui.compose.GoogleAuthButton
import com.wreckingballsoftware.design.ui.login.models.AuthNavigation
import com.wreckingballsoftware.design.ui.login.models.AuthScreenState
import com.wreckingballsoftware.design.ui.navigation.Actions
import com.wreckingballsoftware.design.ui.theme.customTypography
import com.wreckingballsoftware.design.ui.theme.dimensions
import org.koin.androidx.compose.koinViewModel

@Composable
fun AuthScreen(
    actions: Actions,
    viewModel: AuthViewModel = koinViewModel()
) {
    val navigation = viewModel.navigation.collectAsStateWithLifecycle(null)
    navigation.value?.let { nav ->
        when (nav) {
            AuthNavigation.CampaignScreen -> {
                actions.navigateToCampaignsScreen()
            }
        }
    }

    AuthScreenContent(
        state = viewModel.state,
        startSignIn = viewModel::startSignIn,
        getContract = viewModel::getContract,
        handleAuthResult = viewModel::handleAuthResult,
        onDismissAlert = viewModel::onDismissAlert,
    )
}

@Composable
fun AuthScreenContent(
    state: AuthScreenState,
    startSignIn: () -> Unit,
    getContract: () -> ActivityResultContract<Int, Task<GoogleSignInAccount>?>,
    handleAuthResult: (Task<GoogleSignInAccount>?, String) -> Unit,
    onDismissAlert: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = MaterialTheme.dimensions.Space),
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
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.customTypography.DeSignTitle,
            textAlign = TextAlign.Center,
        )

        Spacer(
            modifier = Modifier
                .height(MaterialTheme.dimensions.SpaceLarge)
        )

        Text(
            text = stringResource(id = R.string.sign_in_with_google),
            style = MaterialTheme.customTypography.DeSignSubtitle,
            textAlign = TextAlign.Center,
        )

        Spacer(
            modifier = Modifier
                .height(MaterialTheme.dimensions.SpaceLarge)
        )

        GoogleAuthButton(
            text = stringResource(id = R.string.sign_in),
            isSigningIn = state.isSigningIn,
        ) {
            startSignIn()
            authResultsLauncher.launch(1)
        }


        if (state.errorMessage.isNotEmpty()) {
            DeSignErrorAlert(
                message = state.errorMessage,
                onDismissAlert = onDismissAlert,
            )
        }
    }
}

@Preview(name = "AuthScreenContent Preview")
@Composable
fun AuthScreenContentPreview() {
    AuthScreenContent(
        state = AuthScreenState(),
        startSignIn = { },
        getContract = {
            object : ActivityResultContract<Int, Task<GoogleSignInAccount>?>() {
                override fun createIntent(context: Context, input: Int): Intent {
                    return Intent()
                }

                override fun parseResult(
                    resultCode: Int,
                    intent: Intent?
                ): Task<GoogleSignInAccount>? {
                    return null
                }
            }
         },
        handleAuthResult = { _, _ -> },
        onDismissAlert = { },
    )
}
