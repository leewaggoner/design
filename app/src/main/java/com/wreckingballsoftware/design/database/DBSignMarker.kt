package com.wreckingballsoftware.design.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

const val INVALID_SIGN_MARKER_ID = 0L

@Parcelize
@Entity(tableName = "sign_markers")
data class DBSignMarker(
    @PrimaryKey(autoGenerate = true)
    val id: Long = INVALID_SIGN_MARKER_ID,
    @ColumnInfo(name = "campaign_id")
    val campaignId: Long,
    @ColumnInfo(name = "created_by")
    val createdBy: String,
    val lat: Double,
    val lon: Double,
    @ColumnInfo(name = "date_created")
    val dateCreated: String,
    val notes: String,
) : Parcelable