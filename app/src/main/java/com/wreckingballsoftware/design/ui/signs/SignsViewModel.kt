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

    init {
        viewModelScope.launch(Dispatchers.Main) {
            campaign = campaignsRepo.getCampaign(campaignId = campaignId)
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

    /**
     * This whole thing is pretty ugly. Ideally, I'd like to use BringIntoViewRequester to slide
     * the card into view. But if the card is offscreen, you can't act on it at all. So if
     * it's offscreen, I need to scroll to the initial selection index and only then can the
     * BringIntoViewRequester work it's magic. It seems like there should be a better way to do this.
     */
    suspend fun selectInitialSign() {
        campaign?.let { curCampaign ->
            val curSignId = getInitialSignId(campaignId = curCampaign.id)
            onSignSelected(curSignId)
            val index = mapIdToIndex(id = curSignId)
            state = state.copy(scrollToInitialIndex = if (index == -1) null else index)
        }
    }

    private suspend fun mapIdToIndex(id: Long): Int {
        val signList = signs.first()
        return signList.indexOfFirst { sign ->
            sign.id == id
        }
    }

    private suspend fun getInitialSignId(campaignId: Long): Long {
        var signId = INVALID_SIGN_MARKER_ID
        val selectedSignId = userRepo.getSelectedSignId(default = SelectedSignId())
        if (selectedSignId.signId == INVALID_SIGN_MARKER_ID || (selectedSignId.campaignId != campaignId)) {
            val signList = signs.first()
            if (signList.isNotEmpty()) {
                signId = signList[0].id
            }
        } else {
            signId = selectedSignId.signId
        }
        return signId
    }
}
