package com.wreckingballsoftware.design.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DBCampaign::class], version = 1, exportSchema = false)
abstract class DeSignDatabase : RoomDatabase() {
    abstract fun getCampaignsDao(): CampaignsDao
}