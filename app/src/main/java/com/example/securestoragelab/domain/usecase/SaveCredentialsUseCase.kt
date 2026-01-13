package com.example.securestoragelab.domain.usecase

import com.example.securestoragelab.domain.model.Credentials
import com.example.securestoragelab.domain.repository.SecureStorageRepository

class SaveCredentialsUseCase(
    private val repo: SecureStorageRepository
) {
    suspend operator fun invoke(value: Credentials) {
        repo.saveCredentials(value)
    }
}
