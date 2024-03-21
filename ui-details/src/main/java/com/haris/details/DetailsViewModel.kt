package com.haris.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.annotation.concurrent.Immutable
import javax.inject.Inject

const val ID = "id"
const val NAME = "name"
const val DESCRIPTION = "description"
const val PREP_TIME = "prepTime"
const val COOK_TIME = "cookTime"

@HiltViewModel
class DetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val state: StateFlow<State> =
        MutableStateFlow(
            State(
                id = savedStateHandle.get<String>(ID) ?: "",
                name = savedStateHandle.get<String>(NAME) ?: "",
                description = savedStateHandle.get<String>(DESCRIPTION) ?: "",
                prepTime = savedStateHandle.get<String>(PREP_TIME) ?: "",
                cookTime = savedStateHandle.get<String>(COOK_TIME) ?: ""
            )
        ).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = State()
        )
}

@Immutable
data class State(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val prepTime: String = "",
    val cookTime: String = ""
)