package com.example.securestoragelab.domain.usecase

import com.example.securestoragelab.domain.repository.SecureStorageRepository

class ClearLargeTextUseCase(
    private val repo: SecureStorageRepository
) {
    suspend operator fun invoke(key: String) {
        repo.clearLargeText(key)
    }
}