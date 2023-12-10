package com.wreckingballsoftware.design.ui.campaigns

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import com.wreckingballsoftware.design.database.DBCampaign
import com.wreckingballsoftware.design.database.INVALID_CAMPAIGN_ID
import com.wreckingballsoftware.design.domain.models.ValidInput
import com.wreckingballsoftware.design.repos.CampaignsRepo
import com.wreckingballsoftware.design.repos.UserRepo
import com.wreckingballsoftware.design.ui.campaigns.models.CampaignsScreenNavigation
import com.wreckingballsoftware.design.ui.campaigns.models.CampaignsScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

private const val NO_INDEX = -1

class CampaignsViewModel(
    handle: SavedStateHandle,
    private val campaignsRepo: CampaignsRepo,
    private val userRepo: UserRepo,
) : ViewModel() {
    val navigation = MutableSharedFlow<CampaignsScreenNavigation>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_LATEST,
    )
    @OptIn(SavedStateHandleSaveableApi::class)
    var state by handle.saveable {
        mutableStateOf(CampaignsScreenState())
    }
    val campaigns: Flow<List<DBCampaign>> = campaignsRepo.getAllCampaigns()

    fun onNameValueChanged(text: String) {
        var sanitizedString = text.sanitize()
        sanitizedString = sanitizedString.cutToLength(MAX_NAME_LENGTH)
        state = state.copy(
            campaignName = sanitizedString,
            campaignNameErrorId = 0,
            nameCharactersUsed = sanitizedString.length
        )
    }

    fun onNotesValueChanged(text: String) {
        var sanitizedString = text.sanitize()
        sanitizedString = sanitizedString.cutToLength(MAX_NOTES_LENGTH)
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
                val id = campaignsRepo.addCampaign(newCampaign)
                selectCard(campaignId = id)
                onDismissBottomSheet()
            }
            result = true
        }
        return result
    }

    fun onDismissBottomSheet() {
        showAddCampaignBottomSheet = false
        state = state.copy(
            campaignName = "",
            nameCharactersUsed = 0,
            campaignNotes = "",
            notesCharactersUsed = 0,
        )
    }

    fun onCampaignInfoClick(campaignId: Long) {
        viewModelScope.launch(Dispatchers.Main) {
            selectCard(campaignId = campaignId)
            navigation.emit(CampaignsScreenNavigation.DisplayCampaign(campaignId))
        }
    }

    /**
     * This whole thing is pretty ugly. Ideally, I'd like to use BringIntoViewRequester to slide
     * the card into view. But if the card is offscreen, you can't act on it at all. So if
     * it's offscreen, I need to scroll to the initial selection index and only then can the
     * BringIntoViewRequester work it's magic. It seems like there should be a better way to do this.
     */
    suspend fun selectInitialCampaign() {
        val id = getInitialSelection()
        selectCard(campaignId = id)
        val index = mapIdToIndex(id)
        state = state.copy(scrollToInitialIndex = if (index == NO_INDEX) null else index)
    }

    fun onSelectCard(campaignId: Long) {
        viewModelScope.launch(Dispatchers.Main) {
            selectCard(campaignId)
        }
    }

    fun onDoneScrolling() {
        state = state.copy(scrollToInitialIndex = null)
    }

    private suspend fun selectCard(campaignId: Long) {
        userRepo.putSelectedCampaignId(campaignId)
        state = state.copy(selectedCampaignId = campaignId)
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

    private suspend fun getInitialSelection(): Long {
        var result = INVALID_CAMPAIGN_ID
        val id = userRepo.getSelectedCampaignId()
        val campaignList = campaigns.first()
        if (campaignList.isNotEmpty()) {
            val inList = campaignList.any { dbCampaign ->
                dbCampaign.id == id
            }
            // campaign list is not empty,
            result = if (id == INVALID_CAMPAIGN_ID) {
                // but the selected id is uninitialized -- select the first campaign
                campaignList[0].id
            } else {
                if (inList) {
                    // and the previously selected campaign is in it -- select it
                    id
                } else {
                    // but the previously selected campaign is gone -- select the first campaign
                    campaignList[0].id
                }
            }
        }
        // if the campaign list is empty, invalidate the selected campaign

        return result
    }

    private suspend fun mapIdToIndex(id: Long): Int {
        val campaignList = campaigns.first()
        return campaignList.indexOfFirst { campaign ->
            campaign.id == id
        }
    }

    companion object {
        const val MAX_NAME_LENGTH = 30
        const val MAX_NOTES_LENGTH = 140
        var showAddCampaignBottomSheet by mutableStateOf(false)
        fun showAddCampaignBottomSheet() {
            showAddCampaignBottomSheet = true
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

fun String.cutToLength(length: Int): String =
    if (this.length > length) {
        this.substring(0, length)
    } else {
        this
    }
