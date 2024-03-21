package com.haris.home

import com.haris.data.Result
import com.haris.home.interactors.GetRecipes
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class SensorsViewModelTest {
    @ExperimentalCoroutinesApi
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: HomeViewModel

    private val mockedGetSensorsInteractor = mockk<GetRecipes>()

    @Before
    fun setup() {
        coEvery { mockedGetSensorsInteractor() } returns Unit
        every { mockedGetSensorsInteractor.flow } answers { MutableStateFlow(Result.None()) }

        viewModel = HomeViewModel(mockedGetSensorsInteractor)
    }

    @Test
    fun `UiState - When view model is created the ui state is correct`() =
        runTest {
            val state = viewModel.state.value
            assert(state is ViewState.Empty)
        }
}

@OptIn(ExperimentalCoroutinesApi::class)
class MainDispatcherRule(
    private val testDispatcher: TestDispatcher = StandardTestDispatcher()
) : TestWatcher() {

    override fun starting(description: Description?) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description?) {
        Dispatchers.resetMain()
    }
}
