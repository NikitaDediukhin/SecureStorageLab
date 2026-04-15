package com.example.securestoragelab.data.sharedprefs

import android.content.Context
import com.example.securestoragelab.data.repository.KeyValueStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import androidx.core.content.edit
import com.example.securestoragelab.domain.utils.CryptoEngine

class SharedPrefsStorage(
    context: Context,
    private val cryptoEngine: CryptoEngine
) : KeyValueStorage {

    private val prefs = context.getSharedPreferences("storage_bench", Context.MODE_PRIVATE)

    override suspend fun putString(key: String, value: String) = withContext(Dispatchers.IO) {
        val encrypted = cryptoEngine.encrypt(value)
        prefs.edit(commit = true) { putString(key, encrypted) }
    }

    override suspend fun getString(key: String): String? = withContext(Dispatchers.IO) {
        val encrypted = prefs.getString(key, null) ?: return@withContext null
        runCatching { cryptoEngine.decrypt(encrypted) }.getOrNull()
    }

    override suspend fun putBoolean(key: String, value: Boolean) = withContext(Dispatchers.IO) {
        val encrypted = cryptoEngine.encrypt(value.toString())
        prefs.edit(commit = true) { putString(key, encrypted) }
    }

    override suspend fun getBoolean(key: String): Boolean? = withContext(Dispatchers.IO) {
        val encrypted = prefs.getString(key, null) ?: return@withContext null
        runCatching {
            cryptoEngine.decrypt(encrypted).toBooleanStrictOrNull()
        }.getOrNull()
    }

    override suspend fun remove(key: String) = withContext(Dispatchers.IO) {
        prefs.edit(commit = true) { remove(key) }
    }

    override suspend fun clearAll() = withContext(Dispatchers.IO) {
        prefs.edit(commit = true) { clear() }
    }
}
