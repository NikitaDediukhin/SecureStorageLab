package com.example.securestoragelab.domain.repository

import com.example.securestoragelab.domain.model.StorageMethod

interface StorageRepository {
    suspend fun write(method: StorageMethod, value: String)
    suspend fun read(method: StorageMethod): String?
}