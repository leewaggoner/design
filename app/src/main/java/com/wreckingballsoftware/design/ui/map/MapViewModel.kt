package com.wreckingballsoftware.design.ui.map

import android.annotation.SuppressLint
import android.os.Looper
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Granularity
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import com.wreckingballsoftware.design.database.DBCampaign
import com.wreckingballsoftware.design.database.DBSignMarker
import com.wreckingballsoftware.design.domain.models.CampaignWithMarkers
import com.wreckingballsoftware.design.repos.CampaignsRepo
import com.wreckingballsoftware.design.repos.SignMarkersRepo
import com.wreckingballsoftware.design.repos.UserRepo
import com.wreckingballsoftware.design.ui.campaigns.cutToLength
import com.wreckingballsoftware.design.ui.campaigns.sanitize
import com.wreckingballsoftware.design.ui.map.models.MapScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.concurrent.TimeUnit

class MapViewModel(
    private val userRepo: UserRepo,
    private val signMarkersRepo: SignMarkersRepo,
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    campaignsRepo: CampaignsRepo,
    campaignId: Long,
) : ViewModel() {
    var state: MapScreenState by mutableStateOf(MapScreenState())
    var campaignWithMarkers: Flow<CampaignWithMarkers> =
        campaignsRepo.getCampaignWithMarkers(campaignId).map { element ->
            if (element.keys.isNotEmpty()) {
                val curCampaign = element.keys.toTypedArray()[0]
                campaign = curCampaign
                CampaignWithMarkers(
                    campaign = curCampaign,
                    markers = element[curCampaign] ?: listOf()
                )
            } else {
                CampaignWithMarkers()
            }
        }
    private var campaign: DBCampaign? = null

    init {
        requestLocationUpdates(
            locationCallback = object : LocationCallback() {
                override fun onLocationResult(result: LocationResult) {
                    result.locations.forEach { location ->
                        state = state.copy(
                            myLatLng = LatLng(location.latitude, location.longitude),
                        )
                    }
                }
            },
        )
    }

    fun onNotesValueChanged(text: String) {
        var sanitizedString = text.sanitize()
        sanitizedString = sanitizedString.cutToLength(MAX_NOTES_LENGTH)
        state = state.copy(
            signMarkerNotes = sanitizedString,
            notesCharactersUsed = sanitizedString.length
        )
    }

    fun onAddSignMarker() {
        campaign?.let { curCampaign ->
            viewModelScope.launch(Dispatchers.Main) {
                val formatter = DateTimeFormatter.ofLocalizedDateTime((FormatStyle.SHORT))
                val currentDate = LocalDateTime.now()
                val strDate = currentDate.format(formatter)
                val displayName = userRepo.getUserDisplayName()
                val newMarker = DBSignMarker(
                    campaignId = curCampaign.id,
                    createdBy = displayName,
                    dateCreated = strDate,
                    notes = state.signMarkerNotes,
                    lat = state.myLatLng.latitude,
                    lon = state.myLatLng.longitude,
                )
                signMarkersRepo.addSignMarker(newMarker)
                onDismissBottomSheet()
                state = state.copy(signMarkerNotes = "", notesCharactersUsed = 0)
            }
        }
    }

    fun getCurrentCampaignName(): String {
        return campaign?.name ?: ""
    }

    fun onDismissBottomSheet() {
        showAddSignBottomSheet = false
    }

    fun onDismissAddCampaignMessage() {
        showAddCampaignMessage = false
    }

    fun setMapFocus(latLng: LatLng) {
        state = state.copy(mapLatLng = latLng)
    }

    @SuppressLint("MissingPermission")
    fun requestLocationUpdates(locationCallback: LocationCallback) {
        fusedLocationProviderClient.requestLocationUpdates(
            LocationRequest.Builder(
                Priority.PRIORITY_HIGH_ACCURACY,
                TimeUnit.SECONDS.toMillis(30)
            ).apply {
                setMinUpdateDistanceMeters(1f)
                setGranularity(Granularity.GRANULARITY_PERMISSION_LEVEL)
                setWaitForAccurateLocation(true)
            }.build(),
            locationCallback,
            Looper.getMainLooper(),
        )
    }

    companion object {
        const val MAX_NOTES_LENGTH = 80
        var showAddSignBottomSheet by mutableStateOf(false)
        var showAddCampaignMessage by mutableStateOf(false)
        fun showAddMarkerBottomSheet() {
            showAddSignBottomSheet = true
        }
        fun showAddCampaignMessage() {
            showAddCampaignMessage = true
        }
    }
}