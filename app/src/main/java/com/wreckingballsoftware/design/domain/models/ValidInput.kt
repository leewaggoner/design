package com.wreckingballsoftware.design.domain.models

import com.wreckingballsoftware.design.R

sealed interface ValidInput {
    class InputOk(val text: String) : ValidInput

    sealed class InputError(val errorStringId: Int) : ValidInput {
        class RequiredField : InputError(errorStringId = R.string.required_field)
        class CharacterOverflow: InputError(errorStringId = R.string.too_many)
    }
}