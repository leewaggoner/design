package com.wreckingballsoftware.design.ui.login

import androidx.activity.result.contract.ActivityResultContract
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.wreckingballsoftware.design.domain.GoogleAuth
import com.wreckingballsoftware.design.domain.models.ApiUser
import com.wreckingballsoftware.design.repos.UserRepo
import com.wreckingballsoftware.design.ui.login.models.AuthNavigation
import com.wreckingballsoftware.design.ui.login.models.AuthScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val googleAuth: GoogleAuth,
    private val userRepo: UserRepo,
) : ViewModel() {
    var state by mutableStateOf(AuthScreenState())
    val navigation = MutableSharedFlow<AuthNavigation>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_LATEST,
    )

    fun startSignIn() {
        state = state.copy(isSigningIn = true)
    }

    fun getContract(): ActivityResultContract<Int, Task<GoogleSignInAccount>?> =
        googleAuth.getContract()

    fun handleAuthResult(task: Task<GoogleSignInAccount>?, signInFailedMessage: String) {
        val result = googleAuth.handleAuthResult(task, signInFailedMessage)
        state = if (result.errorMessage.isEmpty()) {
            //successfully signed in
            onSignedIn(result)
            state.copy(
                isSigningIn = false,
            )
        } else {
            //sign in failed
            state.copy(isSigningIn = false, errorMessage = result.errorMessage)
        }
    }

    fun onDismissAlert() {
        state = state.copy(errorMessage = "")
    }

    private fun onSignedIn(user: ApiUser) {
        saveAccountData(
            displayName = user.displayName,
            givenName = user.givenName,
            familyName = user.familyName,
            email = user.email
        )

        //go to campaign screen
        viewModelScope.launch(Dispatchers.Main) {
            navigation.emit(AuthNavigation.CampaignScreen)
        }
    }

    private fun saveAccountData(
        displayName: String?,
        givenName: String?,
        familyName: String?,
        email: String?
    ) {
        viewModelScope.launch(Dispatchers.Main) {
            //save sign in data
            displayName?.let { fullName ->
                userRepo.putUserDisplayName(fullName)
            }
            givenName?.let { firstName ->
                userRepo.putUserGivenName(firstName)
            }
            familyName?.let { lastName ->
                userRepo.putUserFamilyName(lastName)
            }
            email?.let { mail ->
                userRepo.putUserEmail(mail)
            }
        }
    }
}

