package com.wreckingballsoftware.design.ui.campaigns

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.wreckingballsoftware.design.R
import com.wreckingballsoftware.design.database.DBCampaign
import com.wreckingballsoftware.design.ui.campaigns.CampaignsViewModel.Companion.campaignsScreenState
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

    CampaignsScreenContent(
        campaigns = campaigns,
    )

    if (campaignsScreenState.showBottomSheet) {
        AddCampaignBottomSheet(
            textInputParams = viewModel.getTextInputParams(),
            onAddCampaign = viewModel::onAddCampaign,
            onDismissBottomSheet = viewModel::onDismissBottomSheet,
        )
    }
}

@Composable
fun CampaignsScreenContent(
    campaigns: List<DBCampaign>,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        items(
            items = campaigns,
        ) {campaign ->
            Text(text = campaign.name)
        }
    }
}

@Composable
fun getCampaignFrameworkStateItem(): FrameworkStateItem.CampaignsFrameworkStateItem {
    return FrameworkStateItem.CampaignsFrameworkStateItem {
        FloatingActionButton(
            onClick = {
                CampaignsViewModel.showAddCampaignDialog()
            },
        ) {
            Icon(
                imageVector = Icons.Outlined.Add,
                contentDescription = stringResource(id = R.string.create_campaign),
            )
        }
    }
}

@Preview(name = "CampaignScreensContentx Preview")
@Composable
fun CampaignScreensContentPreview() {
    CampaignsScreenContent(
        campaigns = listOf(),
    )
}
