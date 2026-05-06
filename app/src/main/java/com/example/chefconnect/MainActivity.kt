package com.example.chefconnect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.chefconnect.data.network.RetrofitClient
import com.example.chefconnect.data.repository.MealRepository
import com.example.chefconnect.ui.navigation.AppNavHost
import com.example.chefconnect.ui.viewmodel.MealViewModel
import com.example.chefconnect.data.local.FavoritesManager
import com.example.chefconnect.ui.theme.ChefConnectTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = MealRepository(RetrofitClient.api)
        val viewModel = MealViewModel(repository)

        val favoritesManager = FavoritesManager(this)

        setContent {
            ChefConnectTheme (darkTheme = false) {
                AppNavHost(viewModel, favoritesManager)
            }
        }
    }
}