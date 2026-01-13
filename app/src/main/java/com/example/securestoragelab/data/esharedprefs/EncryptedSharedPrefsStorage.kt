package com.example.securestoragelab.data.esharedprefs

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.securestoragelab.data.repository.KeyValueStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import androidx.core.content.edit

class EncryptedSharedPrefsStorage(context: Context) : KeyValueStorage {

    private val prefs by lazy {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        EncryptedSharedPreferences.create(
            context,
            "storage_bench_encrypted", // имя файла
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    override suspend fun putString(key: String, value: String) = withContext(Dispatchers.IO) {
        prefs.edit { putString(key, value) }
    }

    override suspend fun getString(key: String): String? = withContext(Dispatchers.IO) {
        prefs.getString(key, null)
    }

    override suspend fun putBoolean(key: String, value: Boolean) = withContext(Dispatchers.IO) {
        prefs.edit { putBoolean(key, value) }
    }

    override suspend fun getBoolean(key: String): Boolean? = withContext(Dispatchers.IO) {
        if (prefs.contains(key)) prefs.getBoolean(key, false) else null
    }

    override suspend fun remove(key: String) = withContext(Dispatchers.IO) {
        prefs.edit { remove(key) }
    }

    override suspend fun clearAll() = withContext(Dispatchers.IO) {
        prefs.edit().clear().apply()
    }
}