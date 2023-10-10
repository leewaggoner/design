package com.wreckingballsoftware.design.ui.login.models

data class AuthScreenState(
    val isSigningIn: Boolean = false,
    val errorMessage: String = "",
)
