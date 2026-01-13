package com.example.securestoragelab.domain.usecase

import com.example.securestoragelab.domain.repository.SecureStorageRepository

class ClearCredentialsUseCase(
    private val repo: SecureStorageRepository
) {
    suspend operator fun invoke() {
        repo.clearCredentials()
    }
}
