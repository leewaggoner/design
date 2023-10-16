package com.wreckingballsoftware.design.ui.compose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.wreckingballsoftware.design.R
import com.wreckingballsoftware.design.ui.theme.dimensions

@Composable
fun GoogleAuthButton(
    modifier: Modifier = Modifier,
    text: String,
    isSigningIn: Boolean = false,
    onClick: () -> Unit,
) {
    Surface(
        modifier = modifier.then(
            Modifier
                .clickable(
                    enabled = !isSigningIn,
                    onClick = onClick,
                )
        ),
        shape = MaterialTheme.shapes.medium,
        border = BorderStroke(
            width = MaterialTheme.dimensions.BorderStroke,
            color = Color.LightGray
        ),
        color = MaterialTheme.colorScheme.surface,
    ) {
        Row(
            modifier = Modifier
                .padding(all = MaterialTheme.dimensions.Space),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Icon(
                painter = painterResource(id = com.google.android.gms.auth.api.R.drawable.googleg_standard_color_18),
                contentDescription = stringResource(id = R.string.content_description),
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(MaterialTheme.dimensions.SpaceSmall))
            Text(text = if (isSigningIn) {
                    stringResource(id = R.string.signing_in)
                } else {
                    text
            }
            )
        }
    }
}

@Preview
@Composable
fun GoogleAuthButtonPreview() {
    GoogleAuthButton(
        text = stringResource(id = R.string.sign_in),
        isSigningIn = false,
        onClick = { }
    )
}