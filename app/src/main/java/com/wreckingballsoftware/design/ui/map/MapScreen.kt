package com.wreckingballsoftware.design.ui.map

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.wreckingballsoftware.design.R
import com.wreckingballsoftware.design.repos.UserRepo
import com.wreckingballsoftware.design.ui.compose.DeSignErrorAlert
import com.wreckingballsoftware.design.ui.compose.DeSignFab
import com.wreckingballsoftware.design.ui.framework.FrameworkStateItem
import com.wreckingballsoftware.design.ui.map.models.MapScreenState
import com.wreckingballsoftware.design.ui.theme.dimensions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun MapScreen(
    viewModel: MapViewModel = koinViewModel()
) {
    MapScreenContent(
        state = viewModel.state
    )

    if (MapViewModel.showAddSignBottomSheet) {
        AddSignMarkerBottomSheet(
            state = viewModel.state,
            campaignName = viewModel.getCurrentCampaignName(),
            onNotesValueChanged = viewModel::onNotesValueChanged,
            onAddSignMarker = viewModel::onAddSignMarker,
            onDismissBottomSheet = viewModel::onDismissBottomSheet,
        )
    }

    if (MapViewModel.showAddCampaignMessage) {
        DeSignErrorAlert(
            message = stringResource(id = R.string.please_add_campaign),
            onDismissAlert = viewModel::onDismissAddCampaignMessage,
        )
    }
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
fun getMapFrameworkStateItem(userRepo: UserRepo): FrameworkStateItem.MapFrameworkStateItem {
    val scope = rememberCoroutineScope()
    return FrameworkStateItem.MapFrameworkStateItem() {
        DeSignFab(
            modifier = Modifier.padding(end = MaterialTheme.dimensions.MapZoomOffset)
        ) {
            scope.launch(Dispatchers.Main) {
                if (userRepo.getSelectedCampaignId() == 0L) {
                    MapViewModel.showAddCampaignMessage()
                } else {
                    MapViewModel.showAddMarkerBottomSheet()
                }
            }
        }
    }
}
