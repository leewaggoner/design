package com.wreckingballsoftware.design.ui.login.models

sealed interface AuthNavigation {
    data object CampaignScreen : AuthNavigation
}