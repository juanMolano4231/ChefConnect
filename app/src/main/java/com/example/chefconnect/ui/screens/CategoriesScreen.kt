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
import com.example.chefconnect.ui.viewmodel.*
import com.example.chefconnect.ui.navigation.Screen

@Composable
fun CategoriesScreen(
    viewModel: MealViewModel,
    navController: NavController
) {

    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadCategories()
    }

    when (state) {

        is MealState.Loading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is MealState.SuccessCategories -> {
            val categories = (state as MealState.SuccessCategories).categories

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(8.dp)
            ) {
                items(categories) { category ->

                    Card(
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable {
                                navController.navigate(
                                    Screen.Meals.createRoute(category.strCategory)
                                )
                            }
                    ) {
                        Column {
                            AsyncImage(
                                model = category.strCategoryThumb,
                                contentDescription = null,
                                modifier = Modifier.height(120.dp)
                            )
                            Text(
                                text = category.strCategory,
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