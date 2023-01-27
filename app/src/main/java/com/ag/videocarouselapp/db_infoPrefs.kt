package com.ag.videocarouselapp

import android.content.Context
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class db_infoPrefs(context: Context) {
    private val dataStore = context.createDataStore(name = "user_upload_presses")

    companion object {
        val USER_UPLOADS_KEY = preferencesKey<Int>("USER_UPLOADS")
    }

    suspend fun storeUser(uploads: Int) {
        dataStore.edit {
            it[USER_UPLOADS_KEY] = uploads
        }
    }

    val userTitleFlow: Flow<Int> = dataStore.data.map {
        it[USER_UPLOADS_KEY] ?: 0
    }
}