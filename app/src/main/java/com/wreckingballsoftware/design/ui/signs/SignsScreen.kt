package com.wreckingballsoftware.design.ui.signs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.toIntRect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wreckingballsoftware.design.R
import com.wreckingballsoftware.design.database.DBSignMarker
import com.wreckingballsoftware.design.ui.signs.models.SignsScreenState
import com.wreckingballsoftware.design.ui.theme.customTypography
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.ParametersHolder

@Composable
fun SignsScreen(
    campaignId: Long,
    viewModel: SignsViewModel = koinViewModel(
        parameters = { ParametersHolder(mutableListOf(campaignId)) }
    )
) {
    val signs by viewModel.signs.collectAsStateWithLifecycle(
        initialValue = listOf()
    )

    if (viewModel.state.setInitialSelection) {
        LaunchedEffect(key1 = Unit) {
            viewModel.selectInitialSign()
        }
    }

    SignScreenContent(
        state = viewModel.state,
        signs = signs,
        onSignSelected = viewModel::onSignSelected,
        onDoneScrolling = viewModel::onDoneScrolling,
    )
}

@Composable
fun SignScreenContent(
    state: SignsScreenState,
    signs: List<DBSignMarker>,
    onSignSelected: (Long) -> Unit,
    onDoneScrolling: () -> Unit,
) {
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope { Dispatchers.Main }

    //on startup, scroll to the initially selected sign
    state.scrollToInitialIndex?.let { index ->
        LaunchedEffect(key1 = Unit) {
            scrollToInitialIndex(index, listState, scope, onDoneScrolling)
        }
    }

    if (signs.isEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = stringResource(id = R.string.add_sign),
                style = MaterialTheme.customTypography.EmptyListStyle,
                textAlign = TextAlign.Center,
            )
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            state = listState,
        ) {
            items(
                items = signs
            ) { sign ->
                SignCard(
                    campaignName = state.campaignName,
                    selectedSignId = state.selectedSignId,
                    sign = sign,
                    onSignSelected = onSignSelected,
                )
            }
        }
    }
}

private fun scrollToInitialIndex(
    index: Int,
    listState: LazyListState,
    scope: CoroutineScope,
    onDoneScrolling: () -> Unit
) {
    val info = listState.layoutInfo.visibleItemsInfo.firstOrNull { info ->
        if (info.index == index) {
            // if the card is only partially visible at the bottom, return false and scroll to it
            listState.layoutInfo.viewportSize.toIntRect().bottom > info.offset + info.size
        } else {
            false
        }
    }
    if (info == null) {
        scope.launch {
            listState.animateScrollToItem(index = index)
        }
    }
    onDoneScrolling()
}

@Preview(name = "SignScreenContent Preview")
@Composable
fun SignScreenContentPreview() {
    SignScreenContent(
        state = SignsScreenState(),
        signs = listOf(),
        onSignSelected = { },
        onDoneScrolling = { },
    )
}
