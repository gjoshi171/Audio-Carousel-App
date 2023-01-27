package com.ag.videocarouselapp

import android.content.Context
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPrefs(context: Context) {
    private val dataStore = context.createDataStore(name = "user_entry")

    companion object {
        val USER_TITLE = preferencesKey<String>("USER_TITLE")
        val USER_LYRICS = preferencesKey<String>("USER_LYRICS")
    }

    suspend fun storeUser(title: String, lyrics: String) {
        dataStore.edit {
            it[USER_TITLE] = title
            it[USER_LYRICS] = lyrics
        }
    }

    val userTitleFlow: Flow<String> = dataStore.data.map {
        it[USER_TITLE] ?: ""
    }

    val userLyricsFlow: Flow<String> = dataStore.data.map {
        it[USER_LYRICS] ?: ""
    }
}