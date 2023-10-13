package com.wreckingballsoftware.design.ui.campaigns

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wreckingballsoftware.design.R
import com.wreckingballsoftware.design.database.DBCampaign
import com.wreckingballsoftware.design.domain.ValidInput
import com.wreckingballsoftware.design.repos.CampaignRepo
import com.wreckingballsoftware.design.repos.UserRepo
import com.wreckingballsoftware.design.ui.campaigns.models.CampaignsScreenState
import com.wreckingballsoftware.design.ui.compose.TextInputParams
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

    fun onAddCampaign() {
        viewModelScope.launch(Dispatchers.Main) {
            val formatter = DateTimeFormatter.ofLocalizedDateTime((FormatStyle.SHORT))
            val currentDate = LocalDateTime.now()
            val strDate = currentDate.format(formatter)
            val displayName = userRepo.getUserDisplayName()
            val newCampaign = DBCampaign(
                name = campaignsScreenState.campaignName,
                createdBy = displayName,
                dateCreated = strDate,
                notes = campaignsScreenState.campaignNotes,
            )
            campaignRepo.addCampaign(newCampaign)
            onDismissBottomSheet()
        }
    }

    fun onDismissBottomSheet() {
        campaignsScreenState = campaignsScreenState.copy(
            showBottomSheet = false,
            campaignName = "",
            campaignNotes = ""
        )
    }

    fun getTextInputParams(): List<TextInputParams> {
        return listOf(
            TextInputParams(
                text = campaignsScreenState.campaignName,
                labelId = R.string.campaign_name_label,
                singleLine = true,
                onValueChange = { name ->
                    campaignsScreenState = campaignsScreenState.copy(campaignName = name)
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                ),
            ),
            TextInputParams(
                text = campaignsScreenState.campaignNotes,
                labelId = R.string.campaign_notes_label,
                singleLine = false,
                onValueChange = { notes->
                    campaignsScreenState = campaignsScreenState.copy(campaignNotes = notes)
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done,
                ),
                keyboardActions = KeyboardActions(
                    onDone = { onAddCampaign() }
                )
            )
        )
    }

    companion object {
        var campaignsScreenState: CampaignsScreenState by mutableStateOf(CampaignsScreenState())
        fun showAddCampaignDialog() {
            campaignsScreenState = campaignsScreenState.copy(showBottomSheet = true)
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
