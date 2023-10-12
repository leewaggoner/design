package com.wreckingballsoftware.design.ui.campaigns

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.wreckingballsoftware.design.R
import com.wreckingballsoftware.design.database.DBCampaign
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
        state = viewModel.state,
        showDialog = CampaignsViewModel.show,
        campaigns = campaigns,
        onAddCampaign = viewModel::onAddCampaign,
        onCloseAddCampaignDialog = viewModel::onCloseAddCampaignDialog,
        onCampaignNameChange = viewModel::onCampaignNameChange,
        onCampaignNotesChange = viewModel::onCampaignNotesChange,
    )
}

@Composable
fun CampaignsScreenContent(
    state: CampaignsScreenState,
    showDialog: Boolean,
    campaigns: List<DBCampaign>,
    onAddCampaign: () -> Unit,
    onCloseAddCampaignDialog: () -> Unit,
    onCampaignNameChange: (String) -> Unit,
    onCampaignNotesChange: (String) -> Unit,
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

    if (showDialog) {
        val campaignNameParams = TextInputParams(
            text = state.campaignName,
            label = stringResource(id = R.string.campaign_name_label),
            singleLine = true,
            onValueChange = { name ->
                onCampaignNameChange(name)
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
            ),
        )
        val campaignNotesParams = TextInputParams(
            text = state.campaignNotes,
            label = stringResource(id = R.string.campaign_notes_label),
            singleLine = false,
            onValueChange = { notes->
                onCampaignNotesChange(notes)
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done,
            ),
            keyboardActions = KeyboardActions(
                onDone = { onAddCampaign() }
            )
        )

        TextInputDialog(
            title = stringResource(id = R.string.add_campaign_dialog_title),
            message = stringResource(id = R.string.add_campaign_dialog_message),
            inputParams = listOf(
                campaignNameParams,
                campaignNotesParams,
            ),
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
        showDialog = false,
        campaigns = listOf(),
        onAddCampaign = { },
        onCloseAddCampaignDialog = { },
        onCampaignNameChange = { },
        onCampaignNotesChange = { },
    )
}
