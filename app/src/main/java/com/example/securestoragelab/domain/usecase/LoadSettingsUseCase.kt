package com.example.securestoragelab.domain.usecase

import com.example.securestoragelab.domain.model.Settings
import com.example.securestoragelab.domain.repository.SecureStorageRepository

class LoadSettingsUseCase(
    private val repo: SecureStorageRepository
) {
    suspend operator fun invoke(): Settings? = repo.loadSettings()
}
