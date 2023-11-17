package com.wreckingballsoftware.design.ui.map.models

import com.google.android.gms.maps.model.LatLng
import com.wreckingballsoftware.design.database.INVALID_CAMPAIGN_ID

data class MapScreenState(
    val latLng: LatLng = LatLng(32.7157, -117.1611),
    val signMarkerNotes: String = "",
    val notesCharactersUsed: Int = 0,
    val currentCampaignId: Long = INVALID_CAMPAIGN_ID,
)
