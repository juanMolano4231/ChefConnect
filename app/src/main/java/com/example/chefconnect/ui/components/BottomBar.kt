package com.example.chefconnect.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun BottomBar(nav: NavController) {

    NavigationBar {

        NavigationBarItem(
            selected = false,
            onClick = { nav.navigate("categories") },
            icon = { Icon(Icons.Default.Home, null) }
        )

        NavigationBarItem(
            selected = false,
            onClick = { nav.navigate("search") },
            icon = { Icon(Icons.Default.Search, null) }
        )

        NavigationBarItem(
            selected = false,
            onClick = { nav.navigate("favorites") },
            icon = { Icon(Icons.Default.Favorite, null) }
        )
    }
}