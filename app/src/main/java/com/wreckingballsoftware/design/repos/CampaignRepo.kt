package com.wreckingballsoftware.design.repos

import com.wreckingballsoftware.design.database.CampaignsDao
import com.wreckingballsoftware.design.database.DBCampaign
import kotlinx.coroutines.flow.Flow

class CampaignRepo(private val campaignsDao: CampaignsDao) {
    fun getAllCampaigns(): Flow<List<DBCampaign>> = campaignsDao.getAllCampaigns()
}