package com.haris.home

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun Home(isMarkedAsRead: Boolean, navigateToDetails: (Item) -> Unit) {
    Home(hiltViewModel(), navigateToDetails)
}

@Composable
private fun Home(viewModel: HomeViewModel, navigateToDetails: (Item) -> Unit) {
    val state = viewModel.state.collectAsState().value

    LaunchedEffect(Unit) {
        viewModel.markAsRead(isMarkedAsRead)
    }
    if (state.data != null) {
        LazyColumn {
            items(items = state.data.results, key = { it.canonical_id ?: "" }) { item ->
                Item(item = item, navigateToDetails = navigateToDetails)
            }
        }
    }
}

@Composable
private fun Item(item: Item, navigateToDetails: (Item) -> Unit) {
    Card(
        modifier = Modifier.padding(16.dp),
        onClick = { navigateToDetails(item) }) {
        Text(text = item.name ?: "")
    }
}