package com.wreckingballsoftware.design.domain

import com.wreckingballsoftware.design.R

sealed class ValidInput(
    val value: String = "",
    val errorStringId: Int = 0,
) {
    class InputOk(val text: String) : ValidInput(value = text)
    class RequiredField : ValidInput(errorStringId = R.string.required_field)
}