package com.wreckingballsoftware.design.ui.campaigns

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wreckingballsoftware.design.database.DBCampaign
import com.wreckingballsoftware.design.domain.ValidInput
import com.wreckingballsoftware.design.repos.CampaignsRepo
import com.wreckingballsoftware.design.repos.UserRepo
import com.wreckingballsoftware.design.ui.campaigns.models.CampaignsScreenNavigation
import com.wreckingballsoftware.design.ui.campaigns.models.CampaignsScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class CampaignsViewModel(
    private val campaignsRepo: CampaignsRepo,
    private val userRepo: UserRepo,
) : ViewModel() {
    val navigation = MutableSharedFlow<CampaignsScreenNavigation>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_LATEST,
    )
    var state by mutableStateOf(CampaignsScreenState())
    val campaigns: Flow<List<DBCampaign>> = campaignsRepo.getAllCampaigns()

    fun updateCampaigns(campaigns: List<DBCampaign>) {
        state = state.copy(campaigns = campaigns)
    }

    fun onNameValueChanged(text: String) {
        val sanitizedString = text.sanitize()
        state = state.copy(
            campaignName = sanitizedString,
            campaignNameErrorId = 0,
            nameCharactersUsed = sanitizedString.length
        )
    }

    fun onNotesValueChanged(text: String) {
        val sanitizedString = text.sanitize()
        state = state.copy(
            campaignNotes = sanitizedString,
            campaignNotesErrorId = 0,
            notesCharactersUsed = sanitizedString.length
        )
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
                campaignsRepo.addCampaign(newCampaign)
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
            nameCharactersUsed = 0,
            campaignNotes = "",
            notesCharactersUsed = 0,
        )
    }

    fun onCampaignClick(campaignId: Long) {
        viewModelScope.launch(Dispatchers.Main) {
            navigation.emit(CampaignsScreenNavigation.DisplayCampaign(campaignId))
        }
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

fun String.validateCampaignText(optional: Boolean, maxLength: Int) : ValidInput {
    var result: ValidInput = ValidInput.InputOk(this)
    if (!optional && this.isEmpty()) result = ValidInput.InputError.RequiredField()
    if (this.length > maxLength) result = ValidInput.InputError.CharacterOverflow()

    return result
}

fun String.sanitize() =
    this.replace("\\", "_")
        .replace(";", "_").replace("%", "_")
        .replace("\"", "_").replace("\'", "_")
