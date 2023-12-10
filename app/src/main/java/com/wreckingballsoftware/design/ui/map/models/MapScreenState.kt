package com.wreckingballsoftware.design.ui.map.models

import androidx.compose.runtime.mutableStateListOf
import com.google.android.gms.maps.model.LatLng

data class MapScreenState(
    val myLatLng: LatLng = LatLng(32.7157, -117.1611),
    val mapLatLng: LatLng = LatLng(32.7157, -117.1611),
    val markers: List<DesignMarker> = mutableStateListOf(),
    val signMarkerNotes: String = "",
    val notesCharactersUsed: Int = 0,
)
