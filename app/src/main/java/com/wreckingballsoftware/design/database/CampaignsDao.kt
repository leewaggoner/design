package com.wreckingballsoftware.design.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CampaignsDao {
    @Query("SELECT * FROM campaigns")
    fun getAllCampaigns(): Flow<List<DBCampaign>>

    @Query("SELECT * FROM campaigns WHERE id=:id")
    suspend fun getCampaign(id: Long): DBCampaign?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCampaign(campaign: DBCampaign): Long

    @Delete
    suspend fun deleteCampaign(campaign: DBCampaign)

    @Query("DELETE FROM campaigns")
    suspend fun deleteAll()

    @Query("SELECT * FROM campaigns LEFT JOIN sign_markers ON campaigns.id = sign_markers.campaign_id WHERE campaigns.id=:campaignId")
    fun getCampaignWithMarkers(campaignId: Long): Flow<Map<DBCampaign, List<DBSignMarker>>>
}