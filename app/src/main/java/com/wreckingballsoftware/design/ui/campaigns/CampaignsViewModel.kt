package com.wreckingballsoftware.design.ui.campaigns

import androidx.lifecycle.ViewModel
import com.wreckingballsoftware.design.database.DBCampaign
import com.wreckingballsoftware.design.repos.CampaignRepo
import kotlinx.coroutines.flow.Flow

class CampaignsViewModel(
    private val campaignRepo: CampaignRepo,
) : ViewModel() {
    val campaigns: Flow<List<DBCampaign>> = campaignRepo.getAllCampaigns()
}