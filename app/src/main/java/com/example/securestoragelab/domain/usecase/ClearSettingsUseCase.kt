package com.example.securestoragelab.domain.usecase

import com.example.securestoragelab.domain.repository.SecureStorageRepository

class ClearSettingsUseCase(
    private val repo: SecureStorageRepository
) {
    suspend operator fun invoke() {
        repo.clearSettings()
    }
}
