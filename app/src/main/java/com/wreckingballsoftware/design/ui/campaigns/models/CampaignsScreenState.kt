package com.wreckingballsoftware.design.ui.campaigns.models

import com.wreckingballsoftware.design.database.DBCampaign
import com.wreckingballsoftware.design.ui.campaigns.CampaignsViewModel

data class CampaignsScreenState(
    val campaigns: List<DBCampaign> = listOf(),
    val campaignName: String = "",
    val nameCharactersUsed: Int = CampaignsViewModel.MAX_NAME_LENGTH,
    val campaignNameErrorId: Int = 0,
    val campaignNotes: String = "",
    val notesCharactersUsed: Int = CampaignsViewModel.MAX_NOTES_LENGTH,
    val campaignNotesErrorId: Int = 0,
)
