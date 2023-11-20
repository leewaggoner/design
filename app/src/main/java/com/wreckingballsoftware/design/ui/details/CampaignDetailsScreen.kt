package com.wreckingballsoftware.design.ui.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wreckingballsoftware.design.R
import com.wreckingballsoftware.design.database.DBCampaign
import com.wreckingballsoftware.design.ui.compose.DeSignAlert
import com.wreckingballsoftware.design.ui.details.models.CampaignDetailsNavigation
import com.wreckingballsoftware.design.ui.details.models.CampaignDetailsState
import com.wreckingballsoftware.design.ui.navigation.Actions
import com.wreckingballsoftware.design.ui.theme.customTypography
import com.wreckingballsoftware.design.ui.theme.dimensions
import org.koin.androidx.compose.koinViewModel

@Composable
fun CampaignDetailsScreen(
    actions: Actions,
    campaignId: Long,
    viewModel: CampaignDetailsViewModel = koinViewModel(),
) {
    val navigation = viewModel.navigation.collectAsStateWithLifecycle(null)
    navigation.value?.let { nav ->
        when (nav) {
            CampaignDetailsNavigation.Back -> {
                actions.navigateToCampaignsScreen()
            }
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.loadCampaign(campaignId)
    }

    CampaignDetailsScreenContent(
        state = viewModel.state,
        onConfirmDelete = viewModel::onConfirmDelete,
        onDismissDialog = viewModel::onDismissDialog,
        onDelete = viewModel::onDelete,
    )
}

@Composable
fun CampaignDetailsScreenContent(
    state: CampaignDetailsState,
    onConfirmDelete: () -> Unit,
    onDismissDialog: () -> Unit,
    onDelete: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = MaterialTheme.dimensions.Space)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1.0f)
                .verticalScroll(rememberScrollState()),
        ) {
            Spacer(modifier = Modifier.height(MaterialTheme.dimensions.SpaceLarge))
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = state.campaign?.name ?: "",
                style = MaterialTheme.customTypography.DeSignSubtitle,
            )
            Spacer(modifier = Modifier.height(MaterialTheme.dimensions.SpaceSmall))
            Text(
                text = stringResource(
                    id = R.string.created_by,
                    state.campaign?.createdBy ?: "",
                    state.campaign?.dateCreated ?: "",
                ),
                style = MaterialTheme.customTypography.DeSignSmallPrint,
            )
            Spacer(modifier = Modifier.height(MaterialTheme.dimensions.Space))
            Text(
                text = state.campaign?.notes ?: "",
                style = MaterialTheme.customTypography.DeSignBody
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                modifier = Modifier.width(MaterialTheme.dimensions.ButtonWidth),
                onClick = onConfirmDelete,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                ),
            ) {
                Text(
                    text = stringResource(id = R.string.delete),
                )
            }
        }
    }

    if (state.showConfirmDialog) {
        DeSignAlert(
            title = stringResource(id = R.string.confirm_delete_title),
            message = stringResource(id = R.string.confirm_delete_message),
            onDismissRequest = onDismissDialog,
            onConfirmAlert = onDelete,
            onDismissAlert = onDismissDialog,
        )
    }
}

@Preview(name = "CampaignDetailsScreenContent Preview")
@Composable
fun CampaignDetailsScreenContentPreview() {
    CampaignDetailsScreenContent(
        state = CampaignDetailsState(
            campaign = DBCampaign(
                name = "Test Campaign",
                createdBy = "Lee Waggoner",
                dateCreated = "10-17-2023 10:42:32 AM",
                notes = "These are my campaign notes. Gaze upon them in awe. Look " +
                        "on my works, ye Mighty, and despair!"
            )
        ),
        onConfirmDelete = { },
        onDismissDialog = { },
        onDelete = { },
    )
}