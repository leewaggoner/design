package com.wreckingballsoftware.design.ui.campaigns

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.toIntRect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wreckingballsoftware.design.R
import com.wreckingballsoftware.design.database.DBCampaign
import com.wreckingballsoftware.design.ui.campaigns.CampaignsViewModel.Companion.showAddCampaignBottomSheet
import com.wreckingballsoftware.design.ui.campaigns.models.CampaignsScreenNavigation
import com.wreckingballsoftware.design.ui.campaigns.models.CampaignsScreenState
import com.wreckingballsoftware.design.ui.compose.DeSignFab
import com.wreckingballsoftware.design.ui.framework.FrameworkStateItem
import com.wreckingballsoftware.design.ui.navigation.Actions
import com.wreckingballsoftware.design.ui.theme.customTypography
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun CampaignsScreen(
    actions: Actions,
    viewModel: CampaignsViewModel = koinViewModel()
) {
    val navigation = viewModel.navigation.collectAsStateWithLifecycle(initialValue = null)
    navigation.value?.let { nav ->
        when (nav) {
            is CampaignsScreenNavigation.DisplayCampaign -> {
                actions.navigateToCampaignDetailsScreen(nav.campaignId)
            }
        }
    }

    val campaigns by viewModel.campaigns.collectAsStateWithLifecycle(
        initialValue = listOf()
    )

    LaunchedEffect(key1 = Unit) {
        viewModel.onSelectInitialCard()
    }

    CampaignsScreenContent(
        state = viewModel.state,
        campaigns = campaigns,
        onCampaignInfoClick = viewModel::onCampaignInfoClick,
        onSelectCard = viewModel::onSelectCard,
        onDoneScrolling = viewModel::onDoneScrolling,
    )

    if (showAddCampaignBottomSheet) {
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
    campaigns: List<DBCampaign>,
    onCampaignInfoClick: (Long) -> Unit,
    onSelectCard: (Long) -> Unit,
    onDoneScrolling: () -> Unit,
) {
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    //on startup, scroll to the selected card
    state.scrollToIndex?.let { index ->
        val info = listState.layoutInfo.visibleItemsInfo.firstOrNull { info ->
            if (info.index == state.scrollToIndex) {
                // if the card is only partially visible at the bottom, return false and scroll to it
                listState.layoutInfo.viewportSize.toIntRect().bottom > info.offset + info.size
            } else {
                false
            }
        }
        if (info == null) {
            scope.launch {
                listState.animateScrollToItem(index = index)
            }
        }
        onDoneScrolling()
    }

    if (campaigns.isEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = stringResource(id = R.string.add_campaign),
                style = MaterialTheme.customTypography.EmptyListStyle,
                textAlign = TextAlign.Center,
            )
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            state = listState,
        ) {
            items(
                items = campaigns,
            ) { campaign ->
                CampaignCard(
                    selectedCampaignId = state.selectedCampaignId,
                    campaign = campaign,
                    onCampaignInfoClick = onCampaignInfoClick,
                    onSelectCard = onSelectCard
                )
            }
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
        campaigns = listOf(
            DBCampaign(
                name = "First",
                createdBy = "My Mom",
                dateCreated = "11/21/2023"
            )
        ),
        onCampaignInfoClick = { },
        onSelectCard = { },
        onDoneScrolling = { },
    )
}
