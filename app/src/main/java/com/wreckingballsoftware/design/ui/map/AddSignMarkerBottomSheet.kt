package com.wreckingballsoftware.design.ui.map

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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.wreckingballsoftware.design.R
import com.wreckingballsoftware.design.ui.map.models.MapScreenState
import com.wreckingballsoftware.design.ui.theme.customColorsPalette
import com.wreckingballsoftware.design.ui.theme.customTypography
import com.wreckingballsoftware.design.ui.theme.dimensions
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddSignMarkerBottomSheet(
    state: MapScreenState,
    campaignName: String,
    onNotesValueChanged: (String) -> Unit,
    onAddSignMarker: () -> Boolean,
    onDismissBottomSheet: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()

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
                    text = stringResource(
                        id = R.string.add_sign_marker_dialog_title,
                        campaignName,
                    ),
                    style = MaterialTheme.customTypography.DeSignSubtitle,
                    textAlign = TextAlign.Center,
                )

                Spacer(modifier = Modifier.height(MaterialTheme.dimensions.SpaceSmall))

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.add_sign_marker_dialog_message),
                    style = MaterialTheme.customTypography.DeSignBody,
                    textAlign = TextAlign.Center,
                )

                Spacer(modifier = Modifier.height(MaterialTheme.dimensions.SpaceLarge))

                val notesLabel = stringResource(id = R.string.sign_marker_notes_label)
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.signMarkerNotes,
                    label = {
                        Text(text = notesLabel)
                    },
                    placeholder = {
                        Text(text = notesLabel)
                    },
                    supportingText = {
                        Text(
                            text = "${state.notesCharactersUsed}/${MapViewModel.MAX_NOTES_LENGTH}"
                        )
                    },
                    singleLine = false,
                    onValueChange = { text -> onNotesValueChanged(text) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done,
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            onAddSignMarker()
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
                        if (onAddSignMarker()) {
                            scope.launch { sheetState.hide() }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                    ),
                ) {
                    Text(text = stringResource(id = R.string.create_sign_marker))
                }
            }
        }
    }
}


@Preview(name = "AddSignMarkerBottomSheet Preview")
@Composable
fun AddSignMarkerBottomSheetPreview() {
    AddSignMarkerBottomSheet(
        state = MapScreenState(),
        campaignName = "My Campaign",
        onNotesValueChanged = { },
        onAddSignMarker = { true },
        onDismissBottomSheet = { },
    )
}