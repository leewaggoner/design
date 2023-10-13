package com.wreckingballsoftware.design.ui.compose

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions

data class TextInputParams(
    val text: String,
    val labelId: Int,
    val singleLine: Boolean,
    val onValueChange: (String) -> Unit,
    val keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    val keyboardActions: KeyboardActions = KeyboardActions.Default,
)
