package com.haris.pairing

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Main() {
    val navController = rememberNavController()

    AppNavigation(
        modifier = Modifier.fillMaxSize(),
        navController = navController,
    )
}