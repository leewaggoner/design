package com.wreckingballsoftware.design.ui.map

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.wreckingballsoftware.design.ui.map.models.MapScreenState
import com.wreckingballsoftware.design.ui.navigation.Actions
import org.koin.androidx.compose.koinViewModel

@Composable
fun MapScreen(
    actions: Actions,
    viewModel: MapViewModel = koinViewModel()
) {
    MapScreenContent(
        state = viewModel.state
    )
}

@Composable
fun MapScreenContent(
    state: MapScreenState
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = CameraPositionState(
                position = CameraPosition.fromLatLngZoom(state.latLng, 15f)
            ),
            properties = MapProperties(
                isMyLocationEnabled = true,
            )
        ) {
//            Marker(
//                state = MarkerState(position = state.latLng),
//                title = "Singapore",
//                snippet = "Marker in Singapore"
//            )
        }
    }
}