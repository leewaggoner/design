package com.wreckingballsoftware.design.ui.campaigns

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
import androidx.compose.ui.unit.dp
import com.wreckingballsoftware.design.R
import com.wreckingballsoftware.design.database.DBCampaign
import com.wreckingballsoftware.design.ui.theme.customColorsPalette
import com.wreckingballsoftware.design.ui.theme.customTypography

@Composable
fun CampaignCard(
    campaign: DBCampaign,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.then(
            Modifier
                .fillMaxWidth()
                .padding(all = 8.dp),
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.customColorsPalette.surface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = campaign.name,
                style = MaterialTheme.customTypography.DeSignStandardSubtitle,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(
                    id = R.string.created_by,
                    campaign.createdBy,
                    campaign.dateCreated,
                ),
                style = MaterialTheme.customTypography.DeSignSmallPrint
            )
            Spacer(modifier = Modifier.height(16.dp))
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
        )
    )
}