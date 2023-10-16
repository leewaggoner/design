package com.wreckingballsoftware.design.ui.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.wreckingballsoftware.design.ui.details.models.CampaignDetailsState
import com.wreckingballsoftware.design.ui.navigation.Actions
import org.koin.androidx.compose.koinViewModel

@Composable
fun CampaignDetailsScreen(
    actions: Actions,
    campaignId: Long,
    viewModel: CampaignDetailsViewModel = koinViewModel(),
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.loadCampaign(campaignId)
    }

    CampaignDetailsScreenContent(
        state = viewModel.state,
    )
}

@Composable
fun CampaignDetailsScreenContent(
    state: CampaignDetailsState,
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(text = state.campaign?.name ?: "")
    }
}