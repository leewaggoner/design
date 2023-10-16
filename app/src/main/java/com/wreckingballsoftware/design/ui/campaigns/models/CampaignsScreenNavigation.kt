package com.wreckingballsoftware.design.ui.campaigns.models

sealed interface CampaignsScreenNavigation {
    data class DisplayCampaign(val campaignId: Long) : CampaignsScreenNavigation
}