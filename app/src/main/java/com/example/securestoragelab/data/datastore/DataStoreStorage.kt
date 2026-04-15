package com.example.securestoragelab.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.securestoragelab.data.repository.KeyValueStorage
import com.example.securestoragelab.domain.utils.CryptoEngine
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class DataStoreStorage(
    private val context: Context,
    private val cryptoEngine: CryptoEngine
) : KeyValueStorage {

    override suspend fun putString(key: String, value: String) {
        withContext(Dispatchers.IO) {
            val prefKey = stringPreferencesKey(key)
            val encrypted = cryptoEngine.encrypt(value)

            context.dataStore.edit { prefs ->
                prefs[prefKey] = encrypted
            }
        }
    }

    override suspend fun getString(key: String): String? = withContext(Dispatchers.IO) {
        val prefKey = stringPreferencesKey(key)
        val prefs = context.dataStore.data.first()
        val encrypted = prefs[prefKey] ?: return@withContext null

        runCatching { cryptoEngine.decrypt(encrypted) }.getOrNull()
    }

    override suspend fun putBoolean(key: String, value: Boolean) {
        withContext(Dispatchers.IO) {
            val prefKey = stringPreferencesKey(key)
            val encrypted = cryptoEngine.encrypt(value.toString())

            context.dataStore.edit { prefs ->
                prefs[prefKey] = encrypted
            }
        }
    }

    override suspend fun getBoolean(key: String): Boolean? = withContext(Dispatchers.IO) {
        val prefKey = stringPreferencesKey(key)
        val prefs = context.dataStore.data.first()
        val encrypted = prefs[prefKey] ?: return@withContext null

        runCatching {
            cryptoEngine.decrypt(encrypted).toBooleanStrictOrNull()
        }.getOrNull()
    }

    override suspend fun remove(key: String) {
        withContext(Dispatchers.IO) {
            val prefKey = stringPreferencesKey(key)
            context.dataStore.edit { prefs ->
                prefs.remove(prefKey)
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