package com.example.securestoragelab.domain.usecase

import com.example.securestoragelab.domain.repository.SecureStorageRepository

class ClearProfileUseCase(
    private val repo: SecureStorageRepository
) {
    suspend operator fun invoke() {
        repo.clearProfile()
    }
}
