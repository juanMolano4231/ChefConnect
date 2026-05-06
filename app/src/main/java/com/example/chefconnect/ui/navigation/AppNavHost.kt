package com.example.chefconnect.ui.navigation

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding
import androidx.navigation.compose.*
import com.example.chefconnect.data.local.FavoritesManager
import com.example.chefconnect.ui.components.BottomBar
import com.example.chefconnect.ui.screens.*
import com.example.chefconnect.ui.viewmodel.MealViewModel

@Composable
fun AppNavHost(
    viewModel: MealViewModel,
    favoritesManager: FavoritesManager
) {

    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.Detail.route) {
                BottomBar(navController)
            }
        }
    ) { padding ->

        NavHost(
            navController = navController,
            startDestination = Screen.Categories.route,
            modifier = Modifier.padding(padding)
        ) {

            composable(Screen.Categories.route) {
                CategoriesScreen(viewModel, navController)
            }

            composable(Screen.Meals.route) {
                val category = it.arguments?.getString("category") ?: ""
                MealsScreen(viewModel, category, navController)
            }

            composable(Screen.Detail.route) {
                val id = it.arguments?.getString("id") ?: ""
                DetailScreen(id, viewModel, favoritesManager)
            }

            composable("search") {
                SearchScreen(viewModel, navController)
            }

            composable("favorites") {
                FavoritesScreen(viewModel)
            }
        }
    }
}