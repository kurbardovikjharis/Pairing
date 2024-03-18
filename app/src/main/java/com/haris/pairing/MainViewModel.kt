package com.haris.pairing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.annotation.concurrent.Immutable
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    val state: StateFlow<State> = flowOf("haris").map {
        State.Success(it)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = State.Empty
    )
}

@Immutable
sealed class State(
    open val title: String = ""
) {

    data class Success(
        override val title: String,
    ) : State(title)

    data class Error(
        val message: String,
        override val title: String,
    ) : State(title)

    data class Loading(
        override val title: String,
    ) : State(title)

    data object Empty : State()
}