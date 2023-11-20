package com.wreckingballsoftware.design.domain.models

import com.wreckingballsoftware.design.database.DBCampaign
import com.wreckingballsoftware.design.database.DBSignMarker

data class CampaignWithMarkers(
    val campaign: DBCampaign = DBCampaign(),
    val markers: List<DBSignMarker> = listOf(),
)
