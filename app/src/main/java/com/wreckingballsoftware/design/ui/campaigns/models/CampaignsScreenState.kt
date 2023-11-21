package com.wreckingballsoftware.design.ui.campaigns.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CampaignsScreenState(
    val campaignName: String = "",
    val nameCharactersUsed: Int = 0,
    val campaignNameErrorId: Int = 0,
    val campaignNotes: String = "",
    val notesCharactersUsed: Int = 0,
    val campaignNotesErrorId: Int = 0,
    val selectedCampaignId: Long = 0,
    val scrollToIndex: Int? = null,
) : Parcelable
