package com.wreckingballsoftware.design.ui.map

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wreckingballsoftware.design.R
import com.wreckingballsoftware.design.database.INVALID_CAMPAIGN_ID
import com.wreckingballsoftware.design.domain.models.CampaignWithMarkers
import com.wreckingballsoftware.design.repos.UserRepo
import com.wreckingballsoftware.design.ui.compose.DeSignAlert
import com.wreckingballsoftware.design.ui.compose.DeSignErrorAlert
import com.wreckingballsoftware.design.ui.compose.DeSignFab
import com.wreckingballsoftware.design.ui.compose.DeSignMap
import com.wreckingballsoftware.design.ui.framework.FrameworkStateItem
import com.wreckingballsoftware.design.ui.theme.dimensions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.ParametersHolder

@Composable
fun MapScreen(
    campaignId: Long,
    signId: Long,
    viewModel: MapViewModel = koinViewModel(
        parameters = { ParametersHolder(mutableListOf(campaignId, signId)) }
    )
) {
    val campaignWithMarkers by viewModel.campaignWithMarkers.collectAsStateWithLifecycle(
        initialValue = CampaignWithMarkers()
    )

    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        DeSignMap(
            campaignName = campaignWithMarkers.campaign.name,
            markers = campaignWithMarkers.markers,
            mapLatLng = viewModel.state.mapLatLng,
            myLatLng = viewModel.state.myLatLng,
            focusMarkerId = viewModel.state.focusMarkerId,
            setMapFocus = viewModel::setMapFocus,
            onDeleteMarker = viewModel::onConfirmDelete,
        )
    }

    if (viewModel.state.showConfirmDialog) {
        DeSignAlert(
            title = stringResource(id = R.string.confirm_delete_title),
            message = stringResource(id = R.string.confirm_delete_message),
            onDismissRequest = viewModel::onDismissDialog,
            onConfirmAlert = viewModel::onDeleteMarker,
            onDismissAlert = viewModel::onDismissDialog,
        )
    }

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
fun getMapFrameworkStateItem(userRepo: UserRepo): FrameworkStateItem.MapFrameworkStateItem {
    val scope = rememberCoroutineScope { Dispatchers.Main }
    return FrameworkStateItem.MapFrameworkStateItem() {
        DeSignFab(
            modifier = Modifier.padding(end = MaterialTheme.dimensions.MapZoomOffset)
        ) {
            scope.launch {
                if (userRepo.getSelectedCampaignId() == INVALID_CAMPAIGN_ID) {
                    MapViewModel.showAddCampaignMessage()
                } else {
                    MapViewModel.showAddMarkerBottomSheet()
                }
            }
        }
    }
}
