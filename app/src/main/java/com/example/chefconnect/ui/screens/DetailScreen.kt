package com.example.chefconnect.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

    // observar favoritos
    val favorites by favoritesManager.favoritesFlow.collectAsState(initial = emptySet())
    val isFavorite = favorites.contains(id)

    LaunchedEffect(id) {
        viewModel.loadDetail(id)
    }

    if (meal == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AsyncImage(
            model = meal!!.strMealThumb,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        )

        Text(
            text = "ID: ${meal!!.idMeal}",
            style = MaterialTheme.typography.titleMedium
        )

        Text(
            text = meal!!.strMeal,
            style = MaterialTheme.typography.titleLarge
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
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
                            meal!!.idMeal,
                            meal!!.strMeal,
                            message,
                            meal!!.strMealThumb
                        )
                    }
                }
            )
        }
    }
}