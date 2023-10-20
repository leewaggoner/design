package com.wreckingballsoftware.design.ui.map.models

import com.google.android.gms.maps.model.LatLng

data class MapScreenState(
    val latLng: LatLng = LatLng(32.7157, -117.1611),
)