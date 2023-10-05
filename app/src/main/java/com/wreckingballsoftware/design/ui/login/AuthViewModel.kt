package com.wreckingballsoftware.design.ui.login

import androidx.activity.result.contract.ActivityResultContract
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.wreckingballsoftware.design.R
import com.wreckingballsoftware.design.domain.GoogleAuth
import com.wreckingballsoftware.design.repos.UserRepo
import com.wreckingballsoftware.design.ui.login.models.AuthScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthViewModel(
    private val googleAuth: GoogleAuth,
    private val userRepo: UserRepo,
) : ViewModel() {
    var state by mutableStateOf(AuthScreenState())

    init {
        val isSignedIn = isSignedIn()
        val buttonText = if (isSignedIn) R.string.sign_out else R.string.sign_in
        state = state.copy(isSignedIn = isSignedIn, buttonTextId = buttonText)
    }

    fun isSignedIn(): Boolean = googleAuth.isSignedIn()

    fun startSignIn() {
        state = state.copy(isLoading = true)
    }

    fun getContract(): ActivityResultContract<Int, Task<GoogleSignInAccount>?> =
        googleAuth.getContract()

    fun handleAuthResult(task: Task<GoogleSignInAccount>?, signInFailedMessage: String) {
        val result = googleAuth.handleAuthResult(task, signInFailedMessage)
        if (result.errorMessage.isEmpty()) {
            //successfully signed in
            signedIn(
                givenName = result.givenName,
                familyName = result.familyName,
                email = result.email
            )
            state = state.copy(
                isLoading = false,
                isSignedIn = true,
                buttonTextId = R.string.sign_out
            )
        } else {
            //sign in failed
            state = state.copy(isLoading = false, errorMessage = result.errorMessage)
        }
    }

    fun signOut() {
        googleAuth.signOut {
            viewModelScope.launch(Dispatchers.Main) {
                userRepo.putUserGivenName("")
                userRepo.putUserFamilyName("")
                userRepo.putUserEmail("")
            }
            state = state.copy(isSignedIn = false, buttonTextId = R.string.sign_in)
        }
    }

    fun onDismissAlert() {
        state = state.copy(errorMessage = "")
    }

    private fun signedIn(givenName: String?, familyName: String?, email: String?) {
        viewModelScope.launch(Dispatchers.Main) {
            givenName?.let { name ->
                userRepo.putUserGivenName(name)
            }
            familyName?.let { name ->
                userRepo.putUserFamilyName(name)
            }
            email?.let { email ->
                userRepo.putUserEmail(email)
            }
        }
    }
}

