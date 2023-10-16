package com.wreckingballsoftware.design.ui.details.models

import com.wreckingballsoftware.design.database.DBCampaign

data class CampaignDetailsState(
    val campaign: DBCampaign? = null,
)