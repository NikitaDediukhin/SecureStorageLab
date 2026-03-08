package com.example.securestoragelab.domain.usecase

import com.example.securestoragelab.domain.repository.SecureStorageRepository

class SaveLargeTextUseCase(
    private val repo: SecureStorageRepository
) {
    suspend operator fun invoke(key: String, text: String) {
        repo.saveLargeText(key, text)
    }
}