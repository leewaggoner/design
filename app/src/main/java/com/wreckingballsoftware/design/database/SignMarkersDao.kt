package com.wreckingballsoftware.design.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SignMarkersDao {
    @Query("SELECT * FROM sign_markers WHERE campaign_id=:campaignId")
    fun getMarkersForCampaign(campaignId: Long): Flow<List<DBSignMarker>>

    @Query("SELECT * FROM sign_markers WHERE id=:markerId")
    suspend fun getSignMarker(markerId: Long): DBSignMarker

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSignMarker(signMarker: DBSignMarker): Long

    @Delete
    suspend fun deleteSignMarker(signMarker: DBSignMarker)

    @Query("DELETE FROM sign_markers WHERE campaign_id=:campaignId")
    suspend fun deleteSignMarkersFromCampaign(campaignId: Long)

    @Query("DELETE FROM sign_markers")
    suspend fun deleteAll()
}