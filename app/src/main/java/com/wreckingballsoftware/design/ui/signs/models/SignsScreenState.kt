package com.wreckingballsoftware.design.ui.signs.models

import com.wreckingballsoftware.design.database.INVALID_SIGN_MARKER_ID

data class SignsScreenState(
    val campaignName: String = "",
    val setInitialSelection: Boolean = false,
    val scrollToInitialIndex: Int? = null,
    val selectedSignId: Long = INVALID_SIGN_MARKER_ID,
    val showConfirmDialog: Boolean = false,
)