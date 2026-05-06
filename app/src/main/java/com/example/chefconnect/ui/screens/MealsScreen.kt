package com.example.chefconnect.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import androidx.navigation.NavController
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

    LaunchedEffect(category) {
        viewModel.loadMeals(category)
    }

    when (state) {

        is MealState.Loading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is MealState.SuccessMeals -> {
            val meals = (state as MealState.SuccessMeals).meals

            LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                items(meals) { meal ->

                    AppCard (
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable {
                                navController.navigate(
                                    Screen.Detail.createRoute(meal.idMeal)
                                )
                            }
                    ) {
                        Column {
                            AsyncImage(
                                model = meal.strMealThumb,
                                contentDescription = null,
                                modifier = Modifier.height(120.dp)
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

        is MealState.Error -> {
            Text("Error")
        }

        else -> {}
    }
}