package com.wreckingballsoftware.design.ui.login.models

data class AuthScreenState(
    val isSignedIn: Boolean = false,
    val isSigningIn: Boolean = false,
    val errorMessage: String = "",
)
