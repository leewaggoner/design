package com.wreckingballsoftware.design.ui.map

import android.annotation.SuppressLint
import android.os.Looper
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Granularity
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import com.wreckingballsoftware.design.ui.map.models.MapScreenState
import java.util.concurrent.TimeUnit

@SuppressLint("MissingPermission")
class MapViewModel(
    private val fusedLocationProviderClient: FusedLocationProviderClient
) : ViewModel() {
    var state: MapScreenState by mutableStateOf(MapScreenState())

    init {
        fusedLocationProviderClient.requestLocationUpdates(
            LocationRequest.Builder(
                Priority.PRIORITY_HIGH_ACCURACY,
                TimeUnit.SECONDS.toMillis(30)
            ).apply {
                setMinUpdateDistanceMeters(1f)
                setGranularity(Granularity.GRANULARITY_PERMISSION_LEVEL)
                setWaitForAccurateLocation(true)
            }.build(),
            object : LocationCallback() {
                override fun onLocationResult(result: LocationResult) {
                    result.locations.forEach { location ->
                        state = state.copy(
                            latLng = LatLng(
                                location.latitude,
                                location.longitude
                            ),
                        )
                    }
                }
            },
            Looper.getMainLooper(),
        )
    }
}