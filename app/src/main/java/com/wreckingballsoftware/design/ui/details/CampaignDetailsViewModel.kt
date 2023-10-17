package com.wreckingballsoftware.design.ui.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wreckingballsoftware.design.repos.CampaignsRepo
import com.wreckingballsoftware.design.ui.details.models.CampaignDetailsNavigation
import com.wreckingballsoftware.design.ui.details.models.CampaignDetailsState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class CampaignDetailsViewModel(
    private val campaignsRepo: CampaignsRepo,
) : ViewModel() {
    var state: CampaignDetailsState by mutableStateOf(CampaignDetailsState())
    val navigation = MutableSharedFlow<CampaignDetailsNavigation>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_LATEST,
    )

    fun loadCampaign(campaignId: Long) {
        viewModelScope.launch(Dispatchers.Main) {
            val campaign = campaignsRepo.getCampaign(campaignId)
            campaign?.let { viewCampaign ->
                state = state.copy(campaign = viewCampaign)
            }
        }
    }

    fun onConfirmDelete() {
        state = state.copy(showConfirmDialog = true)
    }

    fun onDismissDialog() {
        state = state.copy(showConfirmDialog = false)
    }

    fun onDelete() {
        onDismissDialog()
        viewModelScope.launch(Dispatchers.Main) {
            state.campaign?.let { campaign ->
                campaignsRepo.deleteCampaign(campaign)
            }
            navigation.emit(CampaignDetailsNavigation.Back)
        }
    }
}