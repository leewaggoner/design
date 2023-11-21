package com.wreckingballsoftware.design.repos

import com.wreckingballsoftware.design.database.DBSignMarker
import com.wreckingballsoftware.design.database.SignMarkersDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

/**
 * This class currently looks like a useless layer, but eventually I'll add online storage and
 * synchronization.
 */
class SignMarkersRepo(private val signMarkersDao: SignMarkersDao) {
    fun getMarkersForCampaign(campaignId: Long): Flow<List<DBSignMarker>> =
        signMarkersDao.getMarkersForCampaign(campaignId)

    suspend fun getSignMarker(markerId: Long): DBSignMarker? = withContext(Dispatchers.IO) {
        signMarkersDao.getSignMarker(markerId)
    }

    suspend fun addSignMarker(marker: DBSignMarker) = withContext(Dispatchers.IO) {
        signMarkersDao.insertSignMarker(marker)
    }

    suspend fun deleteSignMarker(marker: DBSignMarker) = withContext(Dispatchers.IO) {
        signMarkersDao.deleteSignMarker(marker)
    }

    suspend fun deleteSignMarkersFromCampaign(campaignId: Long) = withContext(Dispatchers.IO) {
        signMarkersDao.deleteSignMarkersFromCampaign(campaignId)
    }

    suspend fun deleteAll() = withContext(Dispatchers.IO) {
        signMarkersDao.deleteAll()
    }
}