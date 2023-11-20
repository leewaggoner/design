package com.wreckingballsoftware.design.repos

import com.wreckingballsoftware.design.database.CampaignsDao
import com.wreckingballsoftware.design.database.DBCampaign
import com.wreckingballsoftware.design.database.DBSignMarker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class CampaignsRepo(private val campaignsDao: CampaignsDao) {
    fun getAllCampaigns(): Flow<List<DBCampaign>> = campaignsDao.getAllCampaigns()

    suspend fun getCampaign(campaignId: Long): DBCampaign? = withContext(Dispatchers.IO) {
        campaignsDao.getCampaign(campaignId)
    }

    fun getCampaignWithMarkersFlow(campaignId: Long): Flow<Map<DBCampaign, List<DBSignMarker>>> =
        campaignsDao.getCampaignWithMarkersFlow(campaignId)

    suspend fun addCampaign(campaign: DBCampaign) = withContext(Dispatchers.IO) {
        campaignsDao.insertCampaign(campaign)
    }

    suspend fun deleteCampaign(campaign: DBCampaign) = withContext(Dispatchers.IO) {
        campaignsDao.deleteCampaign(campaign)
    }

    suspend fun deleteAll() = withContext(Dispatchers.IO) {
        campaignsDao.deleteAll()
    }
}