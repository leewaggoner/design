package com.wreckingballsoftware.design.ui.compose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wreckingballsoftware.design.R
import com.wreckingballsoftware.design.ui.theme.DangerRed
import com.wreckingballsoftware.design.ui.theme.customTypography
import com.wreckingballsoftware.design.ui.theme.dimensions

@Composable
fun MarkerInfoView(
    title: String,
    snippet: String,
    onDeleteMarker: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .border(
                BorderStroke(1.dp, Color.Black),
                RoundedCornerShape(10)
            )
            .background(Color.White)
            .clip(RoundedCornerShape(10))
            .padding(MaterialTheme.dimensions.SpaceSmall)
    ) {
        Text(
            text = title,
            style = MaterialTheme.customTypography.InfoTitle
        )
        Text(
            text = snippet,
            style = MaterialTheme.customTypography.InfoSnippet
        )
        Button(
            onClick = onDeleteMarker,
            colors = ButtonDefaults.buttonColors(
                containerColor = DangerRed,
            ),
        ) {
            Text(
                text = stringResource(id = R.string.delete)
            )
        }
    }
}

@Preview
@Composable
fun MarkerInfoViewPreview() {
    MarkerInfoView(
        title = "Campaign 1",
        snippet = "Marker on Main Street",
        onDeleteMarker = { }
    )
}