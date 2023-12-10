package com.wreckingballsoftware.design.ui.campaigns

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.wreckingballsoftware.design.R
import com.wreckingballsoftware.design.database.DBCampaign
import com.wreckingballsoftware.design.database.INVALID_CAMPAIGN_ID
import com.wreckingballsoftware.design.ui.theme.AppGold
import com.wreckingballsoftware.design.ui.theme.customColorsPalette
import com.wreckingballsoftware.design.ui.theme.customTypography
import com.wreckingballsoftware.design.ui.theme.dimensions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CampaignCard(
    selectedCampaignId: Long,
    campaign: DBCampaign,
    onCampaignInfoClick: (Long) -> Unit,
    onSelectCard: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scope = rememberCoroutineScope { Dispatchers.Main }
    val requester = remember {
        BringIntoViewRequester()
    }
    val focusRequester = remember {
        FocusRequester()
    }

    Card(
        modifier = modifier.then(
            Modifier
                .focusRequester(focusRequester)
                .bringIntoViewRequester(requester).onFocusEvent {
                    if (it.isFocused) {
                        scope.launch {
                            requester.bringIntoView()
                        }
                    }
                }
                .focusable()
                .fillMaxWidth()
                .padding(all = MaterialTheme.dimensions.SpaceSmall)
                .clickable {
                    focusRequester.requestFocus()
                    onSelectCard(campaign.id)
                },
        ),
        border = if (selectedCampaignId == campaign.id) {
            BorderStroke(MaterialTheme.dimensions.CardBorderStrokeWidth, AppGold)
        } else {
            null
        },
        elevation = CardDefaults.cardElevation(
            defaultElevation = MaterialTheme.dimensions.CardElevation
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.customColorsPalette.surface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = MaterialTheme.dimensions.Space),
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = campaign.name,
                    style = MaterialTheme.customTypography.DeSignSubtitle,
                )
                IconButton(
                    onClick = {
                        onCampaignInfoClick(campaign.id)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Info,
                        contentDescription = stringResource(id = R.string.campaign_details),
                        tint = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }
            Spacer(modifier = Modifier.height(MaterialTheme.dimensions.SpaceSmall))
            Text(
                text = stringResource(
                    id = R.string.created_by,
                    campaign.createdBy,
                    campaign.dateCreated,
                ),
                style = MaterialTheme.customTypography.DeSignSmallPrint
            )
        }
    }
}

@Preview(name = "CampaignCard Preview")
@Composable
fun CampaignCardPreview() {
    CampaignCard(
        selectedCampaignId = INVALID_CAMPAIGN_ID,
        campaign = DBCampaign(
            name = "My Test Campaign",
            createdBy = "Lee Waggoner",
            dateCreated = "10-15-2023 10:46:32 AM",
            notes = "This is my test DeSign campaign."
        ),
        onCampaignInfoClick = { },
        onSelectCard = { },
    )
}