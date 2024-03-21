package com.haris.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.annotation.concurrent.Immutable
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val id: MutableStateFlow<String?> =
        MutableStateFlow(savedStateHandle.get<String>("id"))
    private val desc: MutableStateFlow<String?> =
        MutableStateFlow(savedStateHandle.get<String>("description"))
    private val prepTime: MutableStateFlow<String?> =
        MutableStateFlow(savedStateHandle.get<String>("prepTime"))
    private val cookTime: MutableStateFlow<String?> =
        MutableStateFlow(savedStateHandle.get<String>("cookTime"))

    val state: StateFlow<State> =
        combine(id, desc, prepTime, cookTime) { id, desc, prepTime, cookTime ->
            State(
                id = id ?: "",
                description = desc ?: "",
                prepTime = prepTime ?: "",
                cookTime = cookTime ?: ""
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = State()
        )
}

@Immutable
data class State(
    val id: String = "",
    val description: String = "",
    val prepTime: String = "",
    val cookTime: String = "",
    val instructions: String = ""
)