package com.wreckingballsoftware.design.domain

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.wreckingballsoftware.design.domain.models.ApiUser

class GoogleAuth(
    private val context: Context,
) {
    var googleSignInClient: GoogleSignInClient = GoogleSignIn.getClient(
        context,
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
    )

    fun isSignedIn(): Boolean = lastSignedInAccount() != null

    fun lastSignedInAccount(): GoogleSignInAccount? =
        GoogleSignIn.getLastSignedInAccount(context)

    fun getContract(): ActivityResultContract<Int, Task<GoogleSignInAccount>?> {
        return object : ActivityResultContract<Int, Task<GoogleSignInAccount>?>() {
            override fun createIntent(context: Context, input: Int): Intent =
                googleSignInClient.signInIntent.putExtra("input", input)

            override fun parseResult(resultCode: Int, intent: Intent?): Task<GoogleSignInAccount>? =
                 when (resultCode) {
                    Activity.RESULT_OK -> GoogleSignIn.getSignedInAccountFromIntent(intent)
                    else -> null
                }
        }
    }

    fun handleAuthResult(task: Task<GoogleSignInAccount>?, signInFailedMessage: String): ApiUser {
        var apiUser = ApiUser()
        apiUser = try {
            val account = task?.getResult(ApiException::class.java)
            if (account == null) {
                apiUser.copy(errorMessage = signInFailedMessage)
            } else {
                apiUser.copy(
                    displayName = account.displayName ?: "",
                    givenName = account.givenName ?: "",
                    familyName = account.familyName ?: "",
                    email = account.email ?: "",
                )
            }
        } catch (ex: ApiException) {
            apiUser.copy(errorMessage = "$signInFailedMessage ${ex.message}")
        }
        return apiUser
    }

    fun signOut(onComplete: () -> Unit) {
        googleSignInClient.signOut().addOnCompleteListener {
            onComplete()
        }
    }
}