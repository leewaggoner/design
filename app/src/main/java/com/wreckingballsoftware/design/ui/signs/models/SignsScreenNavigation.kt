package com.wreckingballsoftware.design.ui.signs.models

sealed interface SignsScreenNavigation {
    data class DisplaySignOnMap(val signId: Long) : SignsScreenNavigation
}