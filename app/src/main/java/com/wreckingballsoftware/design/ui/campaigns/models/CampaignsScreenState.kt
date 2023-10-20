package com.wreckingballsoftware.design.ui.campaigns.models

import android.os.Parcelable
import com.wreckingballsoftware.design.database.DBCampaign
import kotlinx.parcelize.Parcelize

@Parcelize
data class CampaignsScreenState(
    val campaigns: List<DBCampaign> = listOf(),
    val campaignName: String = "",
    val nameCharactersUsed: Int = 0,
    val campaignNameErrorId: Int = 0,
    val campaignNotes: String = "",
    val notesCharactersUsed: Int = 0,
    val campaignNotesErrorId: Int = 0,
    val selectedCampaignIndex: Long = 0,
) : Parcelable
