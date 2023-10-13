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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wreckingballsoftware.design.R
import com.wreckingballsoftware.design.ui.compose.TextInputParams
import com.wreckingballsoftware.design.ui.theme.customTypography
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCampaignBottomSheet(
    textInputParams: List<TextInputParams>,
    onAddCampaign: () -> Unit,
    onDismissBottomSheet: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = { onDismissBottomSheet() },
        sheetState = sheetState,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(modifier = Modifier.weight(1.0f)) {
                Text(
                    text = stringResource(id = R.string.add_campaign_dialog_title),
                    style = MaterialTheme.customTypography.DeSignSubtitle
                )
                Spacer(
                    modifier = Modifier
                        .height(8.dp)
                )
                Text(
                    text = stringResource(id = R.string.add_campaign_dialog_message),
                    style = MaterialTheme.customTypography.DeSignBody
                )
                Spacer(
                    modifier = Modifier
                        .height(32.dp)
                )
                textInputParams.forEach { params ->
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = params.text,
                        placeholder = {
                            Text(text = stringResource(id = params.labelId))
                        },
                        singleLine = params.singleLine,
                        onValueChange = { text -> params.onValueChange(text) },
                        keyboardOptions = params.keyboardOptions,
                        keyboardActions = params.keyboardActions,
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Button(
                    modifier = Modifier.width(160.dp),
                    onClick = {
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            onDismissBottomSheet()
                        }
                    }
                ) {
                    Text(text = stringResource(id = R.string.cancel))
                }
                Button(
                    modifier = Modifier.width(160.dp),
                    onClick = {
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            onAddCampaign()
                        }
                    }
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
        textInputParams = listOf(
            TextInputParams(
                text = "",
                labelId = R.string.campaign_name_label,
                singleLine = true,
                onValueChange = { },
                keyboardOptions = KeyboardOptions.Default,
                keyboardActions = KeyboardActions.Default,
            ),
            TextInputParams(
                text = "This is a long piece of notes text that describes a signage campaign.\nIt has multiple line breaks.\nLike this.",
                labelId = R.string.campaign_notes_label,
                singleLine = false,
                onValueChange = { },
                keyboardOptions = KeyboardOptions.Default,
                keyboardActions = KeyboardActions.Default,
            )
        ),
        onAddCampaign = { },
        onDismissBottomSheet = { })
}