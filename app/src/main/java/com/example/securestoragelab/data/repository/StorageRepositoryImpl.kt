package com.example.securestoragelab.data.repository

import com.example.securestoragelab.domain.model.StorageMethod
import com.example.securestoragelab.domain.repository.StorageRepository

class StorageRepositoryImpl(
    private val sharedPrefs: KeyValueStorage,
    private val dataStore: KeyValueStorage
) : StorageRepository {

    override suspend fun write(method: StorageMethod, value: String) {
        storage(method).write(value)
    }

    override suspend fun read(method: StorageMethod): String? {
        return storage(method).read()
    }

    private fun storage(method: StorageMethod): KeyValueStorage =
        when (method) {
            StorageMethod.SHARED_PREFS -> sharedPrefs
            StorageMethod.DATA_STORE -> dataStore
        }
}