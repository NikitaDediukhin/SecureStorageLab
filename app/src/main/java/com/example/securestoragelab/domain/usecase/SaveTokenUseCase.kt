package com.example.securestoragelab.domain.usecase

import com.example.securestoragelab.domain.repository.SecureStorageRepository

class SaveTokenUseCase(
    private val repo: SecureStorageRepository
) {
    suspend operator fun invoke(token: String) {
        repo.saveToken(token)
    }
}
