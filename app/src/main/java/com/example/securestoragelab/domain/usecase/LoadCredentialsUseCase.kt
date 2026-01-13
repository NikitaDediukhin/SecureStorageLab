package com.example.securestoragelab.domain.usecase

import com.example.securestoragelab.domain.model.Credentials
import com.example.securestoragelab.domain.repository.SecureStorageRepository

class LoadCredentialsUseCase(
    private val repo: SecureStorageRepository
) {
    suspend operator fun invoke(): Credentials? = repo.loadCredentials()
}
