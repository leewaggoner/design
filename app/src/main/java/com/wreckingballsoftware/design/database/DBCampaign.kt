package com.wreckingballsoftware.design.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "campaigns")
data class DBCampaign(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    @ColumnInfo(name = "created_by")
    val createdBy: String,
    @ColumnInfo(name = "date_created")
    val dateCreated: String,
    val notes: String,
) : Parcelable
