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
import com.wreckingballsoftware.design.ui.campaigns.models.CampaignInput
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
    var state by mutableStateOf(CampaignsScreenState())
    val campaigns: Flow<List<DBCampaign>> = campaignRepo.getAllCampaigns()

    fun updateCampaigns(campaigns: List<DBCampaign>) {
        state = state.copy(campaigns = campaigns)
    }

    fun onValueChanged(campaignInput: CampaignInput, text: String) {
        state = when (campaignInput) {
            CampaignInput.Name -> text.updateCampaignText(
                state = state,
                campaignInput = CampaignInput.Name,
            )
            CampaignInput.Notes -> text.updateCampaignText(
                state = state,
                campaignInput = CampaignInput.Notes
            )
        }
    }

    fun onAddCampaign(): Boolean {
        var result = false
        val campaignNameIsValid = validateCampaignName(state.campaignName)
        val campaignNoteIsValid = validateCampaignNotes(state.campaignNotes)
        if (campaignNameIsValid && campaignNoteIsValid) {
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
                onDismissBottomSheet()
            }
            result = true
        }
        return result
    }

    fun onDismissBottomSheet() {
        showBottomSheet = false
        state = state.copy(
            campaignName = "",
            campaignNotes = ""
        )
    }

    private fun validateCampaignName(campaignName: String): Boolean {
        var textIsValid = true
        val validName = campaignName.validateCampaignText(
            optional = false,
            maxLength = MAX_NAME_LENGTH,
        )
        state = if (validName is ValidInput.InputError) {
            textIsValid = false
            state.copy(campaignNameErrorId = validName.errorStringId)
        } else {
            state.copy(campaignNameErrorId = 0)
        }
        return textIsValid
    }

    private fun validateCampaignNotes(campaignNotes: String): Boolean {
        var textIsValid = true
        val validNotes = campaignNotes.validateCampaignText(
            optional = true,
            maxLength = MAX_NOTES_LENGTH,
        )
        state = if (validNotes is ValidInput.InputError) {
            textIsValid = false
            state.copy(campaignNotesErrorId = validNotes.errorStringId)
        } else {
            state.copy(campaignNotesErrorId = 0)
        }
        return textIsValid
    }

    companion object {
        const val MAX_NAME_LENGTH = 30
        const val MAX_NOTES_LENGTH = 140
        var showBottomSheet by mutableStateOf(false)
        fun showAddCampaignBottomSheet() {
            showBottomSheet = true
        }
    }
}

fun String.updateCampaignText(
    campaignInput: CampaignInput,
    state: CampaignsScreenState
): CampaignsScreenState {
    //sanitize the input
    val sanitizedString = this.replace("\\", "")
        .replace(";", "").replace("%", "")
        .replace("\"", "").replace("\'", "")

    return when (campaignInput) {
        CampaignInput.Name -> {
            state.copy(
                campaignName = sanitizedString,
                campaignNameErrorId = 0,
                nameCharactersUsed = sanitizedString.length
            )
        }
        CampaignInput.Notes -> {
            state.copy(
                campaignNotes = sanitizedString,
                campaignNotesErrorId = 0,
                notesCharactersUsed = sanitizedString.length
            )
        }
    }
}

fun String.validateCampaignText(optional: Boolean, maxLength: Int) : ValidInput {
    if (!optional && this.isEmpty()) return ValidInput.InputError.RequiredField()
    if (this.length > maxLength) return ValidInput.InputError.CharacterOverflow()

    return ValidInput.InputOk(this)
}
