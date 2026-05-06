package com.example.chefconnect.ui.navigation

sealed class Screen(val route: String) {
    object Categories : Screen("categories")
    object Meals : Screen("meals/{category}") {
        fun createRoute(category: String) = "meals/$category"
    }
    object Detail : Screen("detail/{id}") {
        fun createRoute(id: String) = "detail/$id"
    }
}