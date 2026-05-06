package com.example.chefconnect.data.local

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("favorites")

class FavoritesManager(private val context: Context) {

    private val KEY = stringSetPreferencesKey("fav_ids")

    val favoritesFlow = context.dataStore.data.map {
        it[KEY] ?: emptySet()
    }

    suspend fun toggleFavorite(id: String) {
        context.dataStore.edit { prefs ->
            val current = prefs[KEY] ?: emptySet()
            prefs[KEY] =
                if (current.contains(id)) current - id else current + id
        }
    }
}