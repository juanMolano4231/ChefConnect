package com.example.chefconnect.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import com.example.chefconnect.data.local.FavoritesManager
import com.example.chefconnect.ui.screens.*
import com.example.chefconnect.ui.viewmodel.MealViewModel

@Composable
fun AppNavHost(
    viewModel: MealViewModel,
    favoritesManager: FavoritesManager
)
{

    val navController = rememberNavController()

    NavHost(navController, startDestination = Screen.Categories.route) {

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