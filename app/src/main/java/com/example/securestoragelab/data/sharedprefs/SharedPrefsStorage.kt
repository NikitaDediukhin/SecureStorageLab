package com.example.securestoragelab.data.sharedprefs

import android.content.Context
import com.example.securestoragelab.data.repository.KeyValueStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import androidx.core.content.edit

class SharedPrefsStorage(context: Context) : KeyValueStorage {

    private val prefs = context.getSharedPreferences("storage_bench", Context.MODE_PRIVATE)

    override suspend fun putString(key: String, value: String) = withContext(Dispatchers.IO) {
        prefs.edit(commit = true) { putString(key, value) }
    }

    override suspend fun getString(key: String): String? = withContext(Dispatchers.IO) {
        prefs.getString(key, null)
    }

    override suspend fun putBoolean(key: String, value: Boolean) = withContext(Dispatchers.IO) {
        prefs.edit(commit = true) { putBoolean(key, value) }
    }

    override suspend fun getBoolean(key: String): Boolean? = withContext(Dispatchers.IO) {
        if (prefs.contains(key)) prefs.getBoolean(key, false) else null
    }

    override suspend fun remove(key: String) = withContext(Dispatchers.IO) {
        prefs.edit(commit = true) { remove(key) }
    }

    override suspend fun clearAll() = withContext(Dispatchers.IO) {
        prefs.edit(commit = true) { clear() }
    }
}
