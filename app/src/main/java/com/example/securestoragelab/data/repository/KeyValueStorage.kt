package com.example.securestoragelab.data.repository

interface KeyValueStorage {
    suspend fun write(value: String)
    suspend fun read(): String?
}