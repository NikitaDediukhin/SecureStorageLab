package com.example.securestoragelab.data.repository

import com.example.securestoragelab.domain.model.Credentials
import com.example.securestoragelab.domain.model.Profile
import com.example.securestoragelab.domain.model.Settings
import com.example.securestoragelab.domain.model.StorageMethod
import com.example.securestoragelab.domain.provider.StorageMethodProvider
import com.example.securestoragelab.domain.repository.SecureStorageRepository

class SecureStorageRepositoryImpl(
    private val storages: Map<StorageMethod, KeyValueStorage>,
    private val methodProvider: StorageMethodProvider
) : SecureStorageRepository {

    private fun kv(): KeyValueStorage = storages.getValue(methodProvider.current())

    private object Keys {
        const val LOGIN = "cred_login"
        const val PASSWORD = "cred_password"

        const val TOKEN = "auth_token"

        const val USER_ID = "profile_user_id"
        const val NAME = "profile_name"

        const val THEME = "settings_theme"
        const val BIOMETRICS = "settings_biometrics"
    }

    // S1
    override suspend fun saveCredentials(value: Credentials) {
        kv().putString(Keys.LOGIN, value.login)
        kv().putString(Keys.PASSWORD, value.password)
    }

    override suspend fun loadCredentials(): Credentials? {
        val login = kv().getString(Keys.LOGIN) ?: return null
        val password = kv().getString(Keys.PASSWORD) ?: return null
        return Credentials(login = login, password = password)
    }

    override suspend fun clearCredentials() {
        kv().remove(Keys.LOGIN)
        kv().remove(Keys.PASSWORD)
    }

    // S2
    override suspend fun saveToken(token: String) {
        kv().putString(Keys.TOKEN, token)
    }

    override suspend fun loadToken(): String? {
        return kv().getString(Keys.TOKEN)
    }

    override suspend fun clearToken() {
        kv().remove(Keys.TOKEN)
    }

    // S3
    override suspend fun saveProfile(profile: Profile) {
        kv().putString(Keys.USER_ID, profile.userId)
        kv().putString(Keys.NAME, profile.name)
    }

    override suspend fun loadProfile(): Profile? {
        val userId = kv().getString(Keys.USER_ID) ?: return null
        val name = kv().getString(Keys.NAME) ?: return null
        return Profile(userId = userId, name = name)
    }

    override suspend fun clearProfile() {
        kv().remove(Keys.USER_ID)
        kv().remove(Keys.NAME)
    }

    // S4
    override suspend fun saveSettings(settings: Settings) {
        kv().putString(Keys.THEME, settings.theme)
        kv().putBoolean(Keys.BIOMETRICS, settings.biometrics)
    }

    override suspend fun loadSettings(): Settings? {
        val theme = kv().getString(Keys.THEME) ?: return null
        val biometrics = kv().getBoolean(Keys.BIOMETRICS) ?: return null
        return Settings(theme = theme, biometrics = biometrics)
    }

    override suspend fun clearSettings() {
        kv().remove(Keys.THEME)
        kv().remove(Keys.BIOMETRICS)
    }
}