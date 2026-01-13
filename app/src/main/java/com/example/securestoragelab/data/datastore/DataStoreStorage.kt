package com.example.securestoragelab.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.securestoragelab.data.repository.KeyValueStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class DataStoreStorage(private val context: Context) : KeyValueStorage {

    private val key = stringPreferencesKey("value")

    override suspend fun write(value: String) {
        withContext(Dispatchers.IO) {
            context.dataStore.edit { prefs ->
                prefs[key] = value
            }
        }
    }

    override suspend fun read(): String? = withContext(Dispatchers.IO) {
        val prefs = context.dataStore.data.first()
        prefs[key]
    }
}