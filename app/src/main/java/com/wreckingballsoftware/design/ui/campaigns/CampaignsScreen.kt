package com.wreckingballsoftware.design.ui.campaigns

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wreckingballsoftware.design.ui.campaigns.CampaignsViewModel.Companion.showAddCampaignBottomSheet
import com.wreckingballsoftware.design.ui.campaigns.CampaignsViewModel.Companion.showBottomSheet
import com.wreckingballsoftware.design.ui.campaigns.models.CampaignsScreenNavigation
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
    val navigation = viewModel.navigation.collectAsStateWithLifecycle(null)
    navigation.value?.let { nav ->
        when (nav) {
            is CampaignsScreenNavigation.DisplayCampaign -> {
                actions.navigateToCampaignDetailsScreen(nav.campaignId)
            }
        }
    }

    val campaigns by viewModel.campaigns.collectAsState(
        initial = listOf()
    )
    viewModel.updateCampaigns(campaigns)

    CampaignsScreenContent(
        state = viewModel.state,
        onCampaignClick = viewModel::onCampaignClick,
    )

    if (showBottomSheet) {
        AddCampaignBottomSheet(
            state = viewModel.state,
            onNameValueChanged = viewModel::onNameValueChanged,
            onNotesValueChanged = viewModel::onNotesValueChanged,
            onAddCampaign = viewModel::onAddCampaign,
            onDismissBottomSheet = viewModel::onDismissBottomSheet,
        )
    }
}

@Composable
fun CampaignsScreenContent(
    state: CampaignsScreenState,
    onCampaignClick: (Long) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        items(
            items = state.campaigns,
        ) {campaign ->
            CampaignCard(
                campaign = campaign,
                onCampaignClick = onCampaignClick
            )
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
        onCampaignClick = { },
    )
}
