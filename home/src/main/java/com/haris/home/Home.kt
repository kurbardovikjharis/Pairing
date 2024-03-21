package com.haris.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.haris.home.data.entities.Item
import com.haris.resources.R

@Composable
fun Home(id: String?, navigateToDetails: (Item) -> Unit) {
    Home(
        viewModel = hiltViewModel(),
        id = id,
        navigateToDetails = navigateToDetails,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Home(
    viewModel: HomeViewModel,
    id: String?,
    navigateToDetails: (Item) -> Unit,
) {
    val state = viewModel.state.collectAsState().value

    if (id != null) {
        LaunchedEffect(Unit) {
            viewModel.removeItem(id)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.recipes))
                },
            )
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            HandleState(
                state = state,
                navigateToDetails = navigateToDetails,
                retry = { viewModel.retry() }
            )
        }
    }
}

@Composable
internal fun HandleState(
    state: ViewState,
    navigateToDetails: (Item) -> Unit,
    retry: () -> Unit
) {
    when (state) {
        is ViewState.Success -> {
            Success(
                state = state,
                navigateToDetails = navigateToDetails
            )
        }

        is ViewState.Loading -> {
            Loading(
                state = state,
                navigateToDetails = navigateToDetails
            )
        }

        is ViewState.Error -> {
            Error(
                state = state,
                navigateToDetails = navigateToDetails,
                retry = retry
            )
        }

        is ViewState.Empty -> {}
    }
}

@Composable
private fun Success(
    state: ViewState.Success,
    navigateToDetails: (Item) -> Unit
) {
    Content(items = state.items, navigateToDetails = navigateToDetails)
}

@Composable
private fun Error(
    state: ViewState.Error,
    navigateToDetails: (Item) -> Unit,
    retry: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (state.items.isNotEmpty()) {
            Content(
                items = state.items,
                navigateToDetails = navigateToDetails
            )
        } else {
            Spacer(modifier = Modifier.height(32.dp))
        }

        Text(
            modifier = Modifier.padding(16.dp),
            text = state.message
        )
        Button(onClick = retry) {
            Text(text = stringResource(id = R.string.retry))
        }
    }
}

@Composable
private fun Loading(
    state: ViewState.Loading,
    navigateToDetails: (Item) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (state.items.isNotEmpty()) {
            Content(
                items = state.items,
                navigateToDetails = navigateToDetails
            )
        } else {
            Spacer(modifier = Modifier.height(32.dp))
        }

        CircularProgressIndicator(
            modifier = Modifier
                .size(50.dp)
                .testTag("progress"),
        )
    }
}

@Composable
private fun Content(items: List<Item>, navigateToDetails: (Item) -> Unit) {
    LazyColumn(
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        itemsIndexed(
            items = items,
            key = { _: Int, item: Item -> item.canonicalId }
        ) { index, item ->
            Item(
                item = item,
                navigateToDetails = navigateToDetails,
                isLastItem = index < items.size
            )
        }
    }
}

@Composable
private fun Item(item: Item, navigateToDetails: (Item) -> Unit, isLastItem: Boolean) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        TextButton(
            modifier = Modifier.padding(horizontal = 16.dp),
            onClick = { navigateToDetails(item) }
        ) {
            Text(text = item.name)
        }

        if (isLastItem) {
            HorizontalDivider()
        }
    }
}