package com.wreckingballsoftware.design.ui.map

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.model.LatLng
import com.wreckingballsoftware.design.domain.DeSignMap
import com.wreckingballsoftware.design.ui.map.models.MapScreenState

class MapViewModel(
    private val deSignMap: DeSignMap,
) : ViewModel() {
    var state: MapScreenState by mutableStateOf(MapScreenState())

    init {
        deSignMap.requestLocationUpdates(
            locationCallback = object : LocationCallback() {
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
        )
    }

    @Composable fun DeSignMap(latLng: LatLng) = deSignMap.Map(latLng = latLng)

    companion object {
        fun showAddMarkerBottomSheet() {

        }
    }
}