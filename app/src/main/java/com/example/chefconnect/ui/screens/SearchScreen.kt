package com.example.chefconnect.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kotlinx.coroutines.delay
import androidx.navigation.NavController
import com.example.chefconnect.ui.viewmodel.*
import com.example.chefconnect.ui.components.AppCard
import com.example.chefconnect.ui.navigation.Screen

@Composable
fun SearchScreen(
    viewModel: MealViewModel,
    nav: NavController
) {

    var query by remember { mutableStateOf("") }
    val state by viewModel.searchState.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    // debounce controlado
    LaunchedEffect(query) {
        delay(500)

        if (query.isBlank()) return@LaunchedEffect

        viewModel.searchMeals(query)
    }

    // snackbar SOLO para error real
    LaunchedEffect(state) {
        when (state) {
            is MealState.Error -> {
                val msg = (state as MealState.Error).message

                // evita spam por typing o estados intermedios
                if (query.isNotBlank()) {
                    snackbarHostState.showSnackbar(msg)
                }
            }
            else -> Unit
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            TextField(
                value = query,
                onValueChange = { query = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                placeholder = { Text("Buscar...") },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = null)
                },
                singleLine = true
            )

            when (state) {

                is MealState.Loading -> {
                    Box(
                        Modifier.fillMaxSize(),
                        contentAlignment = androidx.compose.ui.Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is MealState.SuccessMeals -> {
                    val meals = (state as MealState.SuccessMeals).meals

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(8.dp)
                    ) {
                        items(meals) { meal ->

                            AppCard(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .fillMaxWidth()
                                    .clickable {
                                        nav.navigate(
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
                                            .height(140.dp)
                                    )

                                    Text(
                                        text = meal.strMeal,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(8.dp),
                                        style = MaterialTheme.typography.bodyMedium,
                                        maxLines = 2
                                    )
                                }
                            }
                        }
                    }
                }

                is MealState.Error -> {
                    Box(
                        Modifier.fillMaxSize(),
                        contentAlignment = androidx.compose.ui.Alignment.Center
                    ) {
                        Text((state as MealState.Error).message)
                    }
                }

                else -> Unit
            }
        }
    }
}