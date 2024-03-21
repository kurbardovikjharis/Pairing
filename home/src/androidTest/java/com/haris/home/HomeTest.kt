package com.haris.home

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.haris.home.data.entities.Item
import org.junit.Rule
import org.junit.Test

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@get:Rule
val composeTestRule = createComposeRule()

class HomeTest {
    @Test
    fun loadingTest() {
        composeTestRule.setContent {
            HandleState(
                state = ViewState.Loading(
                    items = listOf(
                        Item(
                            name = "NAME",
                            cookTimeMinutes = "",
                            totalTimeMinutes = "",
                            canonicalId = "",
                            description = ""
                        )
                    )
                ),
                navigateToDetails = {},
                retry = {}
            )
        }

        composeTestRule.onNodeWithTag("progress").assertIsDisplayed()
        composeTestRule.onNodeWithText("NAME").assertIsDisplayed()
    }

    @Test
    fun successTest() {
        composeTestRule.setContent {
            HandleState(
                state = ViewState.Success(
                    items = listOf(
                        Item(
                            name = "NAME",
                            cookTimeMinutes = "",
                            totalTimeMinutes = "",
                            canonicalId = "",
                            description = ""
                        )
                    )
                ),
                navigateToDetails = {},
                retry = {}
            )
        }

        composeTestRule.onNodeWithText("NAME").assertIsDisplayed()
    }

    @Test
    fun errorTest() {
        composeTestRule.setContent {
            HandleState(
                state = ViewState.Error(
                    message = "error",
                    items = emptyList(),
                ),
                navigateToDetails = {},
                retry = {}
            )
        }

        composeTestRule.onNodeWithText("error").assertIsDisplayed()
        composeTestRule.onNodeWithText("retry").assertIsDisplayed()
    }
}