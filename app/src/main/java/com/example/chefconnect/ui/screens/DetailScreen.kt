package com.example.chefconnect.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.chefconnect.data.local.FavoritesManager
import com.example.chefconnect.ui.viewmodel.MealViewModel
import kotlinx.coroutines.launch
import androidx.compose.ui.platform.LocalContext
import com.example.chefconnect.notifications.NotificationHelper

@Composable
fun DetailScreen(
    id: String,
    viewModel: MealViewModel,
    favoritesManager: FavoritesManager
) {

    val scope = rememberCoroutineScope()
    val meal by viewModel.detailState.collectAsState()
    val context = LocalContext.current
    val notifier = remember { NotificationHelper(context) }

    val favorites by favoritesManager.favoritesFlow.collectAsState(initial = emptySet())
    val isFavorite by remember(favorites) {
        derivedStateOf { favorites.contains(id) }
    }

    LaunchedEffect(id) {
        viewModel.loadDetail(id)
    }

    if (meal == null) {
        Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    val data = meal!!

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AsyncImage(
            model = data.strMealThumb,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = data.strMeal,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = "ID: ${data.idMeal}",
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(if (isFavorite) "Favorito" else "No favorito")

            Spacer(modifier = Modifier.width(8.dp))

            Switch(
                checked = isFavorite,
                onCheckedChange = {
                    scope.launch {

                        val wasFavorite = isFavorite

                        favoritesManager.toggleFavorite(id)

                        val message = if (wasFavorite) {
                            "Eliminado de favoritos"
                        } else {
                            "Guardado en favoritos"
                        }

                        notifier.showFavoriteNotification(
                            data.idMeal,
                            data.strMeal,
                            message,
                            data.strMealThumb
                        )
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Receta",
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = data.strInstructions ?: "Sin instrucciones disponibles",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Justify,
            modifier = Modifier.fillMaxWidth()
        )
    }
}