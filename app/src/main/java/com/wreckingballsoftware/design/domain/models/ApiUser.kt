package com.wreckingballsoftware.design.domain.models

data class ApiUser(
    val givenName: String = "",
    val familyName: String = "",
    val email: String = "",
    val errorMessage: String = "",
)
