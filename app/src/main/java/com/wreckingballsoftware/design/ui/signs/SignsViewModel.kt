package com.wreckingballsoftware.design.ui.signs

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wreckingballsoftware.design.database.DBCampaign
import com.wreckingballsoftware.design.database.DBSignMarker
import com.wreckingballsoftware.design.database.INVALID_SIGN_MARKER_ID
import com.wreckingballsoftware.design.repos.CampaignsRepo
import com.wreckingballsoftware.design.repos.SelectedSignId
import com.wreckingballsoftware.design.repos.SignMarkersRepo
import com.wreckingballsoftware.design.repos.UserRepo
import com.wreckingballsoftware.design.ui.signs.models.SignsScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SignsViewModel(
    campaignsRepo: CampaignsRepo,
    signsRepo: SignMarkersRepo,
    private val userRepo: UserRepo,
    campaignId: Long,
) : ViewModel() {
    var state: SignsScreenState by mutableStateOf(SignsScreenState())
    val signs: Flow<List<DBSignMarker>> = signsRepo.getMarkersForCampaign(campaignId = campaignId)
    private var campaign: DBCampaign? = null
    private var selectedSignId: SelectedSignId? = null

    init {
        viewModelScope.launch(Dispatchers.Main) {
            campaign = campaignsRepo.getCampaign(campaignId = campaignId)
            selectedSignId = userRepo.getSelectedSignId(default = SelectedSignId())
            campaign?.let { curCampaign ->
                state = state.copy(campaignName = curCampaign.name, setInitialSelection = true)
            }
        }
    }

    fun onSignSelected(signId: Long) {
        campaign?.let { curCampaign ->
            viewModelScope.launch(Dispatchers.Main) {
                userRepo.putSelectedSignId(campaignId = curCampaign.id, signId = signId)
            }
        }
        state = state.copy(selectedSignId = signId, setInitialSelection = false)
    }

    fun onDoneScrolling() {
        state = state.copy(scrollToInitialIndex = null)
    }

    suspend fun selectInitialSign() {
        campaign?.let { curCampaign ->
            val signList = signs.first()
            val curSignId = getInitialSignId(
                campaignId = curCampaign.id,
                signList = signList,
                selectedSignId = selectedSignId ?: SelectedSignId()
            )
            onSignSelected(curSignId)
            state = state.copy()
            val index = mapIdToIndex(curSignId, signList)
            state = state.copy(scrollToInitialIndex = if (index == -1) null else index)
        }
    }

    private fun mapIdToIndex(id: Long, signList: List<DBSignMarker>): Int {
        return signList.indexOfFirst { sign ->
            sign.id == id
        }
    }
    private fun getInitialSignId(
        campaignId: Long,
        signList: List<DBSignMarker>,
        selectedSignId: SelectedSignId
    ): Long {
        var signId = INVALID_SIGN_MARKER_ID
        if (selectedSignId.signId == INVALID_SIGN_MARKER_ID || (selectedSignId.campaignId != campaignId)) {
            if (signList.isNotEmpty()) {
                signId = signList[0].id
            }
        } else {
            signId = selectedSignId.signId
        }
        return signId
    }
}
