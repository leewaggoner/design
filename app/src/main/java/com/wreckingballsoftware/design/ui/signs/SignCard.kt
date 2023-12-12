package com.wreckingballsoftware.design.ui.signs

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.wreckingballsoftware.design.database.DBSignMarker
import com.wreckingballsoftware.design.database.INVALID_SIGN_MARKER_ID
import com.wreckingballsoftware.design.ui.theme.AppGold
import com.wreckingballsoftware.design.ui.theme.DangerRed
import com.wreckingballsoftware.design.ui.theme.customColorsPalette
import com.wreckingballsoftware.design.ui.theme.customTypography
import com.wreckingballsoftware.design.ui.theme.dimensions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SignCard(
    campaignName: String,
    selectedSignId: Long,
    sign: DBSignMarker,
    onSignSelected: (Long) -> Unit,
    onViewMarker: (Long) -> Unit,
    onConfirmDelete: (Long) -> Unit,
) {
    val scope = rememberCoroutineScope { Dispatchers.Main }
    val bringIntoViewRequester = remember {
        BringIntoViewRequester()
    }
    val focusRequester = remember {
        FocusRequester()
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = MaterialTheme.dimensions.Space)
            .focusRequester(focusRequester)
            .bringIntoViewRequester(bringIntoViewRequester)
            .onFocusEvent {
                if (it.isFocused) {
                    scope.launch {
                        bringIntoViewRequester.bringIntoView()
                    }
                }
            }
            .focusable()
            .clickable {
                focusRequester.requestFocus()
                onSignSelected(sign.id)
            },
        border = if (selectedSignId == sign.id) {
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
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Text(
                    text = campaignName,
                    style = MaterialTheme.customTypography.DeSignSubtitle,
                )
                if (sign.notes.isNotEmpty()) {
                    Text(
                        text = sign.notes,
                        style = MaterialTheme.customTypography.DeSignBody,
                    )
                }
                Spacer(modifier = Modifier.height(MaterialTheme.dimensions.SpaceSmall))
                Text(
                    text = stringResource(
                        id = R.string.created_by,
                        sign.createdBy,
                        sign.dateCreated
                    ),
                    style = MaterialTheme.customTypography.DeSignSmallPrint
                )
                Spacer(modifier = Modifier.height(MaterialTheme.dimensions.SpaceSmall))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                ) {
                    Button(
                        modifier = Modifier.width(MaterialTheme.dimensions.SmallButtonWidth),
                        onClick = {
                            onConfirmDelete(sign.id)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = DangerRed,
                        ),
                    ) {
                        Text(text = stringResource(id = R.string.delete))
                    }

                    Button(
                        modifier = Modifier.width(MaterialTheme.dimensions.SmallButtonWidth),
                        onClick = {
                            onViewMarker(sign.id)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                        ),
                    ) {
                        Text(text = stringResource(id = R.string.view))
                    }
                }
            }
        }
    }
}

@Preview(name = "SignCard Preview")
@Composable
fun SignCardPreview() {
    SignCard(
        campaignName = "First",
        selectedSignId = INVALID_SIGN_MARKER_ID,
        sign = DBSignMarker(
            notes = "My first campaign notes.",
            createdBy = "My Mom",
            dateCreated = "11/21/2023, 10:45 AM"
        ),
        onSignSelected = { },
        onViewMarker = { },
        onConfirmDelete = { },
    )
}