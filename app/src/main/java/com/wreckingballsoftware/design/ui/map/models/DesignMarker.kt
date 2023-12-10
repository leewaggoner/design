package com.wreckingballsoftware.design.ui.map.models

import com.google.maps.android.compose.MarkerState

data class DesignMarker(val id: Long, val state: MarkerState, val snippet: String)
