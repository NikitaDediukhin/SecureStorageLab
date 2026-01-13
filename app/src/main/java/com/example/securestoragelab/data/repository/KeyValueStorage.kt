package com.example.securestoragelab.data.repository

interface KeyValueStorage {
    suspend fun putString(key: String, value: String)
    suspend fun getString(key: String): String?
    suspend fun putBoolean(key: String, value: Boolean)
    suspend fun getBoolean(key: String): Boolean?
    suspend fun remove(key: String)
    suspend fun clearAll()
}
