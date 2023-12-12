package com.wreckingballsoftware.design.ui.compose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import com.wreckingballsoftware.design.database.DBSignMarker
import com.wreckingballsoftware.design.ui.map.models.DesignMarker

@Composable
fun DeSignMap(
    campaignName: String,
    mapLatLng: LatLng,
    myLatLng: LatLng,
    markers: List<DBSignMarker>,
    setMapFocus: (LatLng) -> Unit,
    onMapLoaded: () -> Unit,
    onDeleteMarker: (Long) -> Unit,
) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(myLatLng, 15f)
    }

    val markerStates = createMarkerList(markers)
    val builder = LatLngBounds.Builder()
    markerStates.forEach { marker ->
        builder.include(
            LatLng(
                marker.state.position.latitude,
                marker.state.position.longitude
            )
        )
    }

    GoogleMap(
        modifier = Modifier
            .fillMaxSize(),
        cameraPositionState = cameraPositionState,
        properties = MapProperties(
            isMyLocationEnabled = true,
        ),
        onMapLoaded = {
            onMapLoaded()
        }
    ) {
        markerStates.forEach { marker ->
            MarkerInfoWindow(
                tag = marker.id,
                state = marker.state,
                onClick = {
                    marker.state.showInfoWindow()
                    setMapFocus(marker.state.position)
                    false
                },
            ) {
                MarkerInfoView(
                    title = campaignName,
                    snippet = marker.snippet
                ) {
                    onDeleteMarker(marker.id)
                }
            }
        }
    }

    LaunchedEffect(key1 = mapLatLng) {
        cameraPositionState.animate(CameraUpdateFactory.newLatLngZoom(mapLatLng, 15f))
    }
    if (markers.isNotEmpty()) {
        LaunchedEffect(key1 = markerStates) {
            val bounds = builder.build()
            cameraPositionState.move(CameraUpdateFactory.newLatLngBounds(bounds, 64))
        }
    } else {
        LaunchedEffect(key1 = myLatLng) {
            cameraPositionState.move(CameraUpdateFactory.newLatLngZoom(myLatLng, 15f))
        }
    }
}

@Composable
private fun createMarkerList(markers: List<DBSignMarker>): List<DesignMarker> {
    val designMarkers = mutableListOf<DesignMarker>()
    markers.forEach { marker ->
        designMarkers.add(
            DesignMarker(
                id = marker.id,
                state = rememberMarkerState(position = LatLng(marker.lat, marker.lon)),
                snippet = marker.notes
            )
        )
    }
    return designMarkers
}

