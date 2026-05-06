package com.example.chefconnect.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.chefconnect.data.local.FavoritesManager
import com.example.chefconnect.ui.components.AppCard
import com.example.chefconnect.ui.navigation.Screen
import com.example.chefconnect.ui.viewmodel.*

@Composable
fun FavoritesScreen(
    viewModel: MealViewModel,
    favoritesManager: FavoritesManager,
    nav: NavController
) {

    val favorites by favoritesManager.favoritesFlow
        .collectAsState(initial = emptySet())

    val state by viewModel.favoritesState.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(favorites) {
        viewModel.loadFavorites(favorites)
    }

    LaunchedEffect(state) {
        if (state is MealState.Error) {
            snackbarHostState.showSnackbar(
                (state as MealState.Error).message
            )
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->

        Box(modifier = Modifier.padding(padding)) {

            when (state) {

                is MealState.Loading -> {
                    Box(
                        Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is MealState.SuccessMealDetails -> {

                    val meals =
                        (state as MealState.SuccessMealDetails).meals

                    if (meals.isEmpty()) {
                        Box(
                            Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("No favorites yet")
                        }
                    } else {

                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            contentPadding = PaddingValues(8.dp)
                        ) {

                            items(meals) { meal ->

                                AppCard(
                                    modifier = Modifier
                                        .padding(8.dp)
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
                }

                is MealState.Error -> {
                    // handled by snackbar
                }

                else -> {}
            }
        }
    }
}