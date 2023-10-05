package com.wreckingballsoftware.design.ui.login.models

import com.wreckingballsoftware.design.R

data class AuthScreenState(
    val isSignedIn: Boolean = false,
    val isLoading: Boolean = false,
    val buttonTextId: Int = R.string.sign_in,
    val errorMessage: String = "",
)
