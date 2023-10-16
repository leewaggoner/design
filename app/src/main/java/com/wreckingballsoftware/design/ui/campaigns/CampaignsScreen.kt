package com.wreckingballsoftware.design.ui.campaigns

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.wreckingballsoftware.design.ui.campaigns.CampaignsViewModel.Companion.showAddCampaignBottomSheet
import com.wreckingballsoftware.design.ui.campaigns.CampaignsViewModel.Companion.showBottomSheet
import com.wreckingballsoftware.design.ui.campaigns.models.CampaignsScreenState
import com.wreckingballsoftware.design.ui.compose.DeSignFab
import com.wreckingballsoftware.design.ui.framework.FrameworkStateItem
import com.wreckingballsoftware.design.ui.navigation.Actions
import org.koin.androidx.compose.getViewModel

@Composable
fun CampaignsScreen(
    actions: Actions,
    viewModel: CampaignsViewModel = getViewModel()
) {
    val campaigns by viewModel.campaigns.collectAsState(
        initial = listOf()
    )
    viewModel.updateCampaigns(campaigns)

    CampaignsScreenContent(
        state = viewModel.state,
    )

    if (showBottomSheet) {
        AddCampaignBottomSheet(
            state = viewModel.state,
            onValueChanged = viewModel::onValueChanged,
            onAddCampaign = viewModel::onAddCampaign,
            onDismissBottomSheet = viewModel::onDismissBottomSheet,
        )
    }
}

@Composable
fun CampaignsScreenContent(
    state: CampaignsScreenState,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        items(
            items = state.campaigns,
        ) {campaign ->
            CampaignCard(campaign = campaign)
        }
    }
}

@Composable
fun getCampaignFrameworkStateItem(): FrameworkStateItem.CampaignsFrameworkStateItem {
    return FrameworkStateItem.CampaignsFrameworkStateItem {
        DeSignFab {
            showAddCampaignBottomSheet()
        }
    }
}

@Preview(name = "CampaignScreensContentx Preview")
@Composable
fun CampaignScreensContentPreview() {
    CampaignsScreenContent(
        state = CampaignsScreenState(),
    )
}
