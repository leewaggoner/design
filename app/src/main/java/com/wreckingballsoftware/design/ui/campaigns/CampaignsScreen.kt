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
import com.wreckingballsoftware.design.ui.campaigns.models.CampaignsScreenState
import com.wreckingballsoftware.design.ui.compose.TextInputDialog
import com.wreckingballsoftware.design.ui.compose.TextInputParams
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
        state = campaignsScreenState,
        campaigns = campaigns,
        textInputParams = viewModel.getTextInputParamsForDialog(),
        onAddCampaign = viewModel::onAddCampaign,
        onCloseAddCampaignDialog = viewModel::onCloseAddCampaignDialog,
    )
}

@Composable
fun CampaignsScreenContent(
    state: CampaignsScreenState,
    campaigns: List<DBCampaign>,
    textInputParams: List<TextInputParams>,
    onAddCampaign: () -> Unit,
    onCloseAddCampaignDialog: () -> Unit,
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

    if (state.showDialog) {
        TextInputDialog(
            title = stringResource(id = R.string.add_campaign_dialog_title),
            message = stringResource(id = R.string.add_campaign_dialog_message),
            inputParams = textInputParams,
            okText = stringResource(id = R.string.create_campaign),
            okAction = onAddCampaign,
            cancelAction = onCloseAddCampaignDialog
        )
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
        state = CampaignsScreenState(),
        campaigns = listOf(),
        textInputParams = listOf(),
        onAddCampaign = { },
        onCloseAddCampaignDialog = { },
    )
}
