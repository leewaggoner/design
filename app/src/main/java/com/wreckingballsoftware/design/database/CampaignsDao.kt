package com.wreckingballsoftware.design.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CampaignsDao {
    @Query("SELECT * FROM campaigns")
    fun getAllCampaigns(): Flow<List<DBCampaign>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCampaign(campaign: DBCampaign)
}