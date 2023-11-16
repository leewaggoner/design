package com.wreckingballsoftware.design.ui.map

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.model.LatLng
import com.wreckingballsoftware.design.database.DBCampaign
import com.wreckingballsoftware.design.domain.DeSignMap
import com.wreckingballsoftware.design.repos.CampaignsRepo
import com.wreckingballsoftware.design.repos.UserRepo
import com.wreckingballsoftware.design.ui.campaigns.cutToLength
import com.wreckingballsoftware.design.ui.campaigns.sanitize
import com.wreckingballsoftware.design.ui.map.models.MapScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MapViewModel(
    private val deSignMap: DeSignMap,
    private val userRepo: UserRepo,
    campaignsRepo: CampaignsRepo,
) : ViewModel() {
    var state: MapScreenState by mutableStateOf(MapScreenState())
    private var currentCampaign: DBCampaign? = null

    init {
        deSignMap.requestLocationUpdates(
            locationCallback = object : LocationCallback() {
                override fun onLocationResult(result: LocationResult) {
                    result.locations.forEach { location ->
                        state = state.copy(
                            latLng = LatLng(
                                location.latitude,
                                location.longitude
                            ),
                        )
                    }
                }
            },
        )
        viewModelScope.launch(Dispatchers.Main) {
            val currentCampaignId = userRepo.getSelectedCampaignId()
            currentCampaign = campaignsRepo.getCampaign(currentCampaignId)
        }
    }

    fun onNotesValueChanged(text: String) {
        var sanitizedString = text.sanitize()
        sanitizedString = sanitizedString.cutToLength(MAX_NOTES_LENGTH)
        state = state.copy(
            signMarkerNotes = sanitizedString,
            notesCharactersUsed = sanitizedString.length
        )
    }

    fun onAddSignMarker(): Boolean {
//        var result = false
//        val campaignNameIsValid = validateCampaignName(state.campaignName)
//        val campaignNoteIsValid = validateCampaignNotes(state.campaignNotes)
//        if (campaignNameIsValid && campaignNoteIsValid) {
//            viewModelScope.launch(Dispatchers.Main) {
//                val formatter = DateTimeFormatter.ofLocalizedDateTime((FormatStyle.SHORT))
//                val currentDate = LocalDateTime.now()
//                val strDate = currentDate.format(formatter)
//                val displayName = userRepo.getUserDisplayName()
//                val newCampaign = DBCampaign(
//                    name = state.campaignName,
//                    createdBy = displayName,
//                    dateCreated = strDate,
//                    notes = state.campaignNotes,
//                )
//                campaignsRepo.addCampaign(newCampaign)
                onDismissBottomSheet()
//            }
//            result = true
//        }
//        return result
        return true
    }

    fun getCurrentCampaignName(): String {
        return currentCampaign?.name ?: ""
    }

    fun onDismissBottomSheet() {
        showAddSignBottomSheet = false
    }

    fun onDismissAddCampaignMessage() {
        showAddCampaignMessage = false
    }

    @Composable fun DeSignMap(latLng: LatLng) = deSignMap.Map(latLng = latLng)

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