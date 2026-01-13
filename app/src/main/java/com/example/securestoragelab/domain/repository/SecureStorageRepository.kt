package com.example.securestoragelab.domain.repository

import com.example.securestoragelab.domain.model.Credentials
import com.example.securestoragelab.domain.model.Profile
import com.example.securestoragelab.domain.model.Settings

interface SecureStorageRepository {
    suspend fun saveCredentials(value: Credentials)
    suspend fun loadCredentials(): Credentials?
    suspend fun clearCredentials()

    suspend fun saveToken(token: String)
    suspend fun loadToken(): String?
    suspend fun clearToken()

    suspend fun saveProfile(profile: Profile)
    suspend fun loadProfile(): Profile?
    suspend fun clearProfile()

    suspend fun saveSettings(settings: Settings)
    suspend fun loadSettings(): Settings?
    suspend fun clearSettings()
}
