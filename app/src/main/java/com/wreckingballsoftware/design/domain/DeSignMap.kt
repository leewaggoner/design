package com.wreckingballsoftware.design.domain

import android.annotation.SuppressLint
import android.os.Looper
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Granularity
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import com.wreckingballsoftware.design.database.DBSignMarker
import java.util.concurrent.TimeUnit

data class DesignMarker(val state: MarkerState, val snippet: String)

class DeSignMap(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
) {
    @Composable
    fun Map(
        campaignName: String,
        mapLatLng: LatLng,
        myLatLng: LatLng,
        markers: List<DBSignMarker>,
        setMapFocus: (LatLng) -> Unit,
    ) {
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(myLatLng, 15f)
        }

        val markerStates = mutableListOf<DesignMarker>()
        val builder = LatLngBounds.Builder()
        markers.forEach { marker ->
            markerStates.add(
                DesignMarker(
                    rememberMarkerState(position = LatLng(marker.lat, marker.lon)),
                    marker.notes
                )
            )
            builder.include(LatLng(marker.lat, marker.lon))
        }

        GoogleMap(
            modifier = Modifier
                .fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = MapProperties(
                isMyLocationEnabled = true,
            ),
        ) {
            markerStates.forEach { designState ->
                Marker(
                    state = designState.state,
                    title = campaignName,
                    snippet = designState.snippet,
                    onClick = {
                        designState.state.showInfoWindow()
                        setMapFocus(designState.state.position)
                        false
                    },
                )
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

    @SuppressLint("MissingPermission")
    fun requestLocationUpdates(locationCallback: LocationCallback) {
        fusedLocationProviderClient.requestLocationUpdates(
            LocationRequest.Builder(
                Priority.PRIORITY_HIGH_ACCURACY,
                TimeUnit.SECONDS.toMillis(30)
            ).apply {
                setMinUpdateDistanceMeters(1f)
                setGranularity(Granularity.GRANULARITY_PERMISSION_LEVEL)
                setWaitForAccurateLocation(true)
            }.build(),
            locationCallback,
            Looper.getMainLooper(),
        )
    }
}