package com.wreckingballsoftware.design.ui.campaigns

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.wreckingballsoftware.design.R
import com.wreckingballsoftware.design.database.DBCampaign
import com.wreckingballsoftware.design.ui.theme.customColorsPalette
import com.wreckingballsoftware.design.ui.theme.customTypography
import com.wreckingballsoftware.design.ui.theme.dimensions

@Composable
fun CampaignCard(
    campaign: DBCampaign,
    onCampaignClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.then(
            Modifier
                .fillMaxWidth()
                .padding(all = MaterialTheme.dimensions.SpaceSmall)
                .clickable {
                    onCampaignClick(campaign.id)
                },
        ),
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
            Text(
                text = campaign.name,
                style = MaterialTheme.customTypography.DeSignStandardSubtitle,
            )
            Spacer(modifier = Modifier.height(MaterialTheme.dimensions.SpaceSmall))
            Text(
                text = stringResource(
                    id = R.string.created_by,
                    campaign.createdBy,
                    campaign.dateCreated,
                ),
                style = MaterialTheme.customTypography.DeSignSmallPrint
            )
            Spacer(modifier = Modifier.height(MaterialTheme.dimensions.Space))
            Text(
                text = campaign.notes,
                style = MaterialTheme.customTypography.DeSignStandardBody
            )
        }
    }
}

@Preview(name = "CampaignCard Preview")
@Composable
fun CampaignCardPreview() {
    CampaignCard(
        campaign = DBCampaign(
            name = "My Test Campaign",
            createdBy = "Lee Waggoner",
            dateCreated = "10-15-2023 10:46:32 AM",
            notes = "This is my test DeSign campaign."
        ),
        onCampaignClick = { }
    )
}