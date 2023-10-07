package com.wreckingballsoftware.design.ui.campaigns

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wreckingballsoftware.design.domain.GoogleAuth
import com.wreckingballsoftware.design.repos.UserRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CampaignsViewModel(
    private val googleAuth: GoogleAuth,
    private val userRepo: UserRepo,
) : ViewModel() {
    fun onSignOut() {
        googleAuth.signOut {
            viewModelScope.launch(Dispatchers.Main) {
                userRepo.putUserGivenName("")
                userRepo.putUserFamilyName("")
                userRepo.putUserEmail("")
            }
        }
    }
}