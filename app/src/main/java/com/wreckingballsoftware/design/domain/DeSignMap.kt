package com.wreckingballsoftware.design.domain

import android.annotation.SuppressLint
import android.os.Looper
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Granularity
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import java.util.concurrent.TimeUnit

class DeSignMap(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
) {
    @Composable
    fun Map(latLng: LatLng) =
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = CameraPositionState(
                position = CameraPosition.fromLatLngZoom(latLng, 15f)
            ),
            properties = MapProperties(
                isMyLocationEnabled = true,
            ),
        ) {
//            Marker(
//                state = MarkerState(position = state.latLng),
//                title = "Singapore",
//                snippet = "Marker in Singapore"
//            )
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