package com.example.securestoragelab.data.sharedprefs

import android.content.Context
import com.example.securestoragelab.data.repository.KeyValueStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import androidx.core.content.edit

class SharedPrefsStorage(context: Context) : KeyValueStorage {

    private val prefs = context.getSharedPreferences("storage_bench", Context.MODE_PRIVATE)

    override suspend fun write(value: String) = withContext(Dispatchers.IO) {
        prefs.edit { putString("value", value) }
    }

    override suspend fun read(): String? = withContext(Dispatchers.IO) {
        prefs.getString("value", null)
    }
}