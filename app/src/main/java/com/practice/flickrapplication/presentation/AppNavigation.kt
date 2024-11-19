package com.practice.flickrapplication.presentation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.practice.flickrapplication.viewmodel.MainViewModel

@Composable
fun NavigationGraph(
    navController: NavHostController,
    viewModel: MainViewModel,
    innerPadding: PaddingValues
) {
    NavHost(navController = navController, startDestination = "search") {
        composable("search") {
            ImageSearchScreen(navController = navController, viewModel = viewModel, innerPadding)
        }
        composable("imageDetail") {
            ImageDetailScreen(viewModel = viewModel, innerPadding, navController)
        }
    }
}