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
import com.example.chefconnect.data.local.FavoritesManager
import com.example.chefconnect.ui.components.AppCard
import com.example.chefconnect.ui.navigation.Screen
import com.example.chefconnect.ui.viewmodel.MealViewModel

@Composable
fun FavoritesScreen(
    viewModel: MealViewModel,
    favoritesManager: FavoritesManager,
    nav: NavController
) {

    val favorites by favoritesManager.favoritesFlow.collectAsState(initial = emptySet())

    // lista de detalles cargados
    val meals = remember { mutableStateListOf<com.example.chefconnect.data.model.MealDetail>() }

    LaunchedEffect(favorites) {
        meals.clear()

        favorites.forEach { id ->
            try {
                val res = viewModel.getDetail(id)
                res.meals.firstOrNull()?.let { meals.add(it) }
            } catch (_: Exception) {}
        }
    }

    if (meals.isEmpty()) {
        Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("No favorites yet")
        }
        return
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp)
    ) {

        items(meals) { meal ->

            AppCard (
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .clickable {
                        nav.navigate(
                            Screen.Detail.createRoute(meal.idMeal)
                        )
                    }
            ) {
                Column {

                    AsyncImage(
                        model = meal.strMealThumb,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                    )

                    Text(
                        text = meal.strMeal,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}