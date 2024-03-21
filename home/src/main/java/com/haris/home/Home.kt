package com.haris.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
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

    HandleState(
        state = state,
        navigateToDetails = navigateToDetails,
        retry = { viewModel.retry() }
    )
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
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(items = items, key = { it.canonicalId }) { item ->
            Item(item = item, navigateToDetails = navigateToDetails)
        }
    }
}

@Composable
private fun Item(item: Item, navigateToDetails: (Item) -> Unit) {
    Card(
        onClick = { navigateToDetails(item) }
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = item.name
        )
    }
}