package com.haris.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.haris.resources.R

@Composable
fun Details(navigateUp: () -> Unit, markAsRead: (String) -> Unit) {
    Details(hiltViewModel(), navigateUp, markAsRead)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Details(
    viewModel: DetailsViewModel,
    navigateUp: () -> Unit,
    markAsRead: (String) -> Unit
) {
    val state = viewModel.state.collectAsState().value
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.details))
                },
                navigationIcon = {
                    IconButton(onClick = navigateUp) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                            contentDescription = stringResource(id = R.string.back_button_acc)
                        )
                    }
                },
            )
        }
    ) {
        Box(
            modifier = Modifier.padding(it),
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = { markAsRead(state.id) }
                ) {
                    Text(text = stringResource(id = R.string.mark_as_read))
                }
                Text(text = state.prepTime)
                Text(text = state.cookTime)
                Text(text = state.description)
            }
        }
    }
}