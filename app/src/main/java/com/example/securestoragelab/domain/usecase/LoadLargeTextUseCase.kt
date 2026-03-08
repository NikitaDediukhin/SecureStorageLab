package com.example.securestoragelab.domain.usecase

import com.example.securestoragelab.domain.repository.SecureStorageRepository

class LoadLargeTextUseCase(
    private val repo: SecureStorageRepository
) {
    suspend operator fun invoke(key: String): String? {
        return repo.loadLargeText(key)
    }
}