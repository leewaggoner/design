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
    suspend fun insertCampaign(campaign: DBCampaign)

    @Delete
    suspend fun deleteCampaign(campaign: DBCampaign)

    @Query("DELETE FROM campaigns")
    suspend fun deleteAll()
}