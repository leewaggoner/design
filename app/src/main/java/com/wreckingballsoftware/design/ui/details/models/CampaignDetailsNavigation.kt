package com.wreckingballsoftware.design.ui.details.models

sealed interface CampaignDetailsNavigation {
    data object Back : CampaignDetailsNavigation
}