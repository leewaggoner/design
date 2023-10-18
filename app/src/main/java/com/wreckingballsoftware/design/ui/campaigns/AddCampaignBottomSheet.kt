package com.wreckingballsoftware.design.ui.campaigns

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.wreckingballsoftware.design.R
import com.wreckingballsoftware.design.ui.campaigns.models.CampaignsScreenState
import com.wreckingballsoftware.design.ui.theme.customColorsPalette
import com.wreckingballsoftware.design.ui.theme.customTypography
import com.wreckingballsoftware.design.ui.theme.dimensions
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCampaignBottomSheet(
    state: CampaignsScreenState,
    onNameValueChanged: (String) -> Unit,
    onNotesValueChanged: (String) -> Unit,
    onAddCampaign: () -> Boolean,
    onDismissBottomSheet: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusRequester = remember { FocusRequester() }
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    )

    LaunchedEffect(key1 = Unit) {
        focusRequester.requestFocus()
    }

    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = { onDismissBottomSheet() },
        sheetState = sheetState,
        containerColor = MaterialTheme.customColorsPalette.surface,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = MaterialTheme.dimensions.Space),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(modifier = Modifier.weight(1.0f)) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.add_campaign_dialog_title),
                    style = MaterialTheme.customTypography.DeSignSubtitle,
                    textAlign = TextAlign.Center,
                )

                Spacer(modifier = Modifier.height(MaterialTheme.dimensions.SpaceSmall))

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.add_campaign_dialog_message),
                    style = MaterialTheme.customTypography.DeSignBody,
                    textAlign = TextAlign.Center,
                )

                Spacer(modifier = Modifier.height(MaterialTheme.dimensions.SpaceLarge))

                var nameLabel = stringResource(id = R.string.campaign_name_label)
                OutlinedTextField(
                    modifier = Modifier
                        .focusRequester(focusRequester)
                        .fillMaxWidth(),
                    value = state.campaignName,
                    label = {
                        Text(text = nameLabel)
                    },
                    placeholder = {
                        Text(text = nameLabel)
                    },
                    supportingText = {
                        if (state.campaignNameErrorId == 0) {
                            Text(
                                text = "${state.nameCharactersUsed}/${CampaignsViewModel.MAX_NAME_LENGTH}"
                            )
                        } else {
                            Text(
                                text = stringResource(id = state.campaignNameErrorId),
                                color = Color.Red,
                            )
                        }
                    },
                    singleLine = true,
                    onValueChange = { text -> onNameValueChanged(text) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next,
                    ),
                )

                Spacer(modifier = Modifier.height(MaterialTheme.dimensions.Space))

                val notesLabel = stringResource(id = R.string.campaign_notes_label)
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.campaignNotes,
                    label = {
                        Text(text = notesLabel)
                    },
                    placeholder = {
                        Text(text = notesLabel)
                    },
                    supportingText = {
                        if (state.campaignNotesErrorId == 0) {
                            Text(
                                text = "${state.notesCharactersUsed}/${CampaignsViewModel.MAX_NOTES_LENGTH}"
                            )
                        } else {
                            Text(
                                text = stringResource(id = state.campaignNotesErrorId),
                                color = Color.Red,
                            )
                        }
                    },
                    singleLine = false,
                    onValueChange = { text -> onNotesValueChanged(text) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done,
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            onAddCampaign()
                            scope.launch { sheetState.hide() }
                        }
                    ),
                )

                Spacer(modifier = Modifier.height(MaterialTheme.dimensions.SpaceLarge))
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = MaterialTheme.dimensions.Space),
                horizontalArrangement = Arrangement.SpaceAround,
            ) {
                Button(
                    modifier = Modifier.width(MaterialTheme.dimensions.ButtonWidth),
                    onClick = {
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            onDismissBottomSheet()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                    ),
                ) {
                    Text(text = stringResource(id = R.string.cancel))
                }

                Button(
                    modifier = Modifier.width(MaterialTheme.dimensions.ButtonWidth),
                    onClick = {
                        if (onAddCampaign()) {
                            scope.launch { sheetState.hide() }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                    ),
                ) {
                    Text(text = stringResource(id = R.string.create_campaign))
                }
            }
        }
    }
}

@Preview(name = "AddCampaignBottomSheet Preview")
@Composable
fun AddCampaignBottomSheetPreview() {
    AddCampaignBottomSheet(
        state = CampaignsScreenState(),
        onNameValueChanged = { },
        onNotesValueChanged = { },
        onAddCampaign = { true },
        onDismissBottomSheet = { },
    )
}