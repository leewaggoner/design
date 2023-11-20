package com.wreckingballsoftware.design.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DBCampaign::class, DBSignMarker::class], version = 4, exportSchema = false)
abstract class DeSignDatabase : RoomDatabase() {
    abstract fun getCampaignsDao(): CampaignsDao
    abstract fun getSignMarkersDao(): SignMarkersDao
}