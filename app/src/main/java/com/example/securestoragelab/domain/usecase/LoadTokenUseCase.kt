package com.example.securestoragelab.domain.usecase

import com.example.securestoragelab.domain.repository.SecureStorageRepository

class LoadTokenUseCase(
    private val repo: SecureStorageRepository
) {
    suspend operator fun invoke(): String? = repo.loadToken()
}