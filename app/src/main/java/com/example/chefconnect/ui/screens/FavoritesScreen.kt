package com.example.chefconnect.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import androidx.navigation.NavController
import com.example.chefconnect.ui.viewmodel.*
import com.example.chefconnect.ui.navigation.Screen
@Composable
fun FavoritesScreen(viewModel: MealViewModel) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Favorites (persistencia OK)")
    }
}