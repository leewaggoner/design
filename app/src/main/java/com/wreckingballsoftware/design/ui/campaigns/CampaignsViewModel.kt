package com.wreckingballsoftware.design.ui.campaigns

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wreckingballsoftware.design.database.DBCampaign
import com.wreckingballsoftware.design.domain.ValidInput
import com.wreckingballsoftware.design.repos.CampaignRepo
import com.wreckingballsoftware.design.repos.UserRepo
import com.wreckingballsoftware.design.ui.campaigns.models.CampaignsScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class CampaignsViewModel(
    private val campaignRepo: CampaignRepo,
    private val userRepo: UserRepo,
) : ViewModel() {
    val campaigns: Flow<List<DBCampaign>> = campaignRepo.getAllCampaigns()
    var state by mutableStateOf(CampaignsScreenState())

    fun onAddCampaign() {
        viewModelScope.launch(Dispatchers.Main) {
            val formatter = DateTimeFormatter.ofLocalizedDateTime((FormatStyle.SHORT))
            val currentDate = LocalDateTime.now()
            val strDate = currentDate.format(formatter)
            val displayName = userRepo.getUserDisplayName()
            val newCampaign = DBCampaign(
                name = state.campaignName,
                createdBy = displayName,
                dateCreated = strDate,
                notes = state.campaignNotes,
            )
            campaignRepo.addCampaign(newCampaign)
            onCloseAddCampaignDialog()
        }
    }

    fun onCampaignNameChange(text: String) {
        state = state.copy(campaignName = text)
    }

    fun onCampaignNotesChange(text: String) {
        state = state.copy(campaignNotes = text)
    }

    fun onCloseAddCampaignDialog() {
        state = state.copy(campaignName = "", campaignNotes = "")
        show = false
    }

    companion object {
        var show by mutableStateOf(false)
        fun showAddCampaignDialog() {
            show = true
        }
    }
}

fun String.validateCampaignText(text: String, optional: Boolean) : ValidInput {
    if (!optional && text.isEmpty()) return ValidInput.RequiredField()

    //sanitize the input
    val result = text.replace("\\", "")
        .replace(";", "").replace("%", "")
        .replace("\"", "").replace("\'", "")
    return ValidInput.InputOk(result)
}