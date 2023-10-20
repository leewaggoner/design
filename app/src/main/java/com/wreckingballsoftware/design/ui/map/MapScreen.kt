package com.wreckingballsoftware.design.ui.map

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wreckingballsoftware.design.ui.compose.DeSignFab
import com.wreckingballsoftware.design.ui.framework.FrameworkStateItem
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
    state: MapScreenState,
    viewModel: MapViewModel = koinViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        viewModel.DeSignMap(latLng = state.latLng)
    }
}

@Composable
fun getMapFrameworkStateItem(): FrameworkStateItem.MapFrameworkStateItem {
    return FrameworkStateItem.MapFrameworkStateItem {
        DeSignFab(
            modifier = Modifier.padding(end = 48.dp)
        ) {
            MapViewModel.showAddMarkerBottomSheet()
        }
    }
}
