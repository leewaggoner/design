package com.wreckingballsoftware.design.domain

import android.annotation.SuppressLint
import android.os.Looper
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.MarkerState
import com.wreckingballsoftware.design.database.DBSignMarker
import java.util.concurrent.TimeUnit

class DeSignMap(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
) {
    @Composable
    fun Map(campaignName: String, latLng: LatLng, markers: List<DBSignMarker>) =
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = CameraPositionState(
                position = CameraPosition.fromLatLngZoom(latLng, 15f)
            ),
            properties = MapProperties(
                isMyLocationEnabled = true,
            ),
        ) {
            markers.forEach { marker ->
                MarkerInfoWindow(
                    state = MarkerState(position = LatLng(marker.lat, marker.lon))
                ) { clickMarker ->
                    Column(
                        modifier = Modifier
                            .border(
                                BorderStroke(1.dp, Color.Black),
                                RoundedCornerShape(10)
                            )
                            .clip(RoundedCornerShape(10))
                            .background(Color.Red)
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            Icons.Filled.Info,
                            contentDescription = "",
                            modifier = Modifier.size(24.dp)
                        )
                        Text(clickMarker.title ?: "Default")
                        Text(clickMarker.snippet ?: "Default snippet")
                    }
                }
//                Marker(
//                    state = MarkerState(position = LatLng(marker.lat, marker.lon)),
//                    title = campaignName,
//                    snippet = marker.notes,
//                    onClick = { clickMarker ->
//                        Log.e("-----LEE-----", clickMarker.snippet ?: "")
//                        false
//                    }
//                )
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