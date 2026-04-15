package com.example.securestoragelab.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.securestoragelab.data.repository.KeyValueStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class DataStoreStorage(private val context: Context) : KeyValueStorage {

    override suspend fun putString(key: String, value: String) {
        withContext(Dispatchers.IO) {
            val prefKey = stringPreferencesKey(key)
            context.dataStore.edit { prefs ->
                prefs[prefKey] = value
            }
        }
    }

    override suspend fun getString(key: String): String? = withContext(Dispatchers.IO) {
        val prefKey = stringPreferencesKey(key)
        val prefs = context.dataStore.data.first()
        prefs[prefKey]
    }

    override suspend fun putBoolean(key: String, value: Boolean) {
        withContext(Dispatchers.IO) {
            val prefKey = booleanPreferencesKey(key)
            context.dataStore.edit { prefs ->
                prefs[prefKey] = value
            }
        }
    }

    override suspend fun getBoolean(key: String): Boolean? = withContext(Dispatchers.IO) {
        val prefKey = booleanPreferencesKey(key)
        val prefs = context.dataStore.data.first()
        prefs[prefKey] // nullable, если ключа нет
    }

    override suspend fun remove(key: String) {
        withContext(Dispatchers.IO) {
            val sKey = stringPreferencesKey(key)
            val bKey = booleanPreferencesKey(key)

            context.dataStore.edit { prefs ->
                prefs.remove(sKey)
                prefs.remove(bKey)
            }
        }
    }

    override suspend fun clearAll() {
        withContext(Dispatchers.IO) {
            context.dataStore.edit { prefs ->
                prefs.clear()
            }
        }
    }
}
