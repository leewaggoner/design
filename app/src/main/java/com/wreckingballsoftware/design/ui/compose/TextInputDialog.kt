package com.wreckingballsoftware.design.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.wreckingballsoftware.design.R
import com.wreckingballsoftware.design.ui.theme.customTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextInputDialog(
    title: String,
    message: String,
    inputParams: List<TextInputParams>,
    okText: String,
    okAction: () -> Unit,
    cancelAction: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Dialog(
        onDismissRequest = { cancelAction() }
    ) {
        Card(
            modifier = modifier.then(
                Modifier
                    .fillMaxWidth()
                    .height(375.dp)
                    .padding(all = 16.dp),
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.customTypography.DeSignSubtitle
                )
                Spacer(modifier = Modifier
                    .height(8.dp)
                )
                Text(
                    text = message,
                    style = MaterialTheme.customTypography.DeSignBody
                )
                Spacer(modifier = Modifier
                    .height(16.dp)
                )
                inputParams.forEach { params ->
                    OutlinedTextField(
                        value = params.text,
                        placeholder = { Text(text = params.label) },
                        singleLine = params.singleLine,
                        onValueChange = { text -> params.onValueChange(text) },
                        keyboardOptions = params.keyboardOptions,
                        keyboardActions = params.keyboardActions,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Button(
                        modifier = Modifier.width(100.dp),
                        onClick = { cancelAction() }
                    ) {
                        Text(text = stringResource(id = R.string.cancel))
                    }
                    Button(
                        modifier = Modifier.width(160.dp),
                        onClick = { okAction() }
                    ) {
                        Text(text = okText)
                    }
                }
            }
        }
    }
}

@Preview(name = "TextInputDialog Preview")
@Composable
fun TextInputDialogPreview() {
    TextInputDialog(
        title = "My Text Input Dialog Title",
        message = "Enter some text, please",
        inputParams = listOf(
            TextInputParams(
                text = "",
                label = "Campaign Name",
                singleLine = true,
                onValueChange = { },
                keyboardOptions = KeyboardOptions.Default,
                keyboardActions = KeyboardActions.Default,
            ),
            TextInputParams(
                text = "This is a long piece of notes text that describes a signage campaign.\nIt has multiple line breaks.\nLike this.",
                label = "Campaign Notes",
                singleLine = false,
                onValueChange = { },
                keyboardOptions = KeyboardOptions.Default,
                keyboardActions = KeyboardActions.Default,
            )
        ),
        okText = "Save Campaign",
        okAction = { },
        cancelAction = { }
    )
}