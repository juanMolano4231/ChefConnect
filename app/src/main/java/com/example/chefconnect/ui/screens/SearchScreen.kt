package com.example.chefconnect.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kotlinx.coroutines.delay
import androidx.navigation.NavController
import com.example.chefconnect.ui.viewmodel.*

@Composable
fun SearchScreen(viewModel: MealViewModel, nav: NavController) {

    var query by remember { mutableStateOf("") }
    val state by viewModel.searchState.collectAsState()

    LaunchedEffect(query) {
        delay(500)
        if (query.isNotBlank()) viewModel.searchMeals(query)
    }

    Column {
        TextField(
            value = query,
            onValueChange = { query = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Buscar...") }
        )

        when (state) {

            is MealState.SuccessMeals -> {
                val meals = (state as MealState.SuccessMeals).meals

                LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                    items(meals) {
                        Card(modifier = Modifier.padding(8.dp)) {
                            Column {
                                AsyncImage(
                                    model = it.strMealThumb,
                                    contentDescription = null,
                                    modifier = Modifier.height(120.dp)
                                )
                                Text(it.strMeal)
                            }
                        }
                    }
                }
            }

            else -> {}
        }
    }
}