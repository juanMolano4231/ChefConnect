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
import com.example.chefconnect.ui.components.AppCard
import com.example.chefconnect.ui.viewmodel.*
import com.example.chefconnect.ui.navigation.Screen

@Composable
fun MealsScreen(
    viewModel: MealViewModel,
    category: String,
    navController: NavController
) {

    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(category) {
        viewModel.loadMeals(category)
    }

    LaunchedEffect(state) {
        if (state is MealState.Error) {
            snackbarHostState.showSnackbar(
                message = (state as MealState.Error).message
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

                is MealState.SuccessMeals -> {
                    val meals =
                        (state as MealState.SuccessMeals).meals

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(8.dp)
                    ) {
                        items(meals) { meal ->

                            AppCard(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .clickable {
                                        navController.navigate(
                                            Screen.Detail.createRoute(meal.idMeal)
                                        )
                                    }
                            ) {
                                Column(
                                    modifier = Modifier.fillMaxWidth()
                                ) {

                                    AsyncImage(
                                        model = meal.strMealThumb,
                                        contentDescription = null,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(120.dp)
                                    )

                                    Text(
                                        text = meal.strMeal,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(8.dp),
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }
                        }
                    }
                }

                is MealState.Error -> {
                    // UI vacía: error manejado por Snackbar
                }

                else -> {}
            }
        }
    }
}