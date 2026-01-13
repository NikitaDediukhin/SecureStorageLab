package com.example.securestoragelab.domain.usecase

import com.example.securestoragelab.domain.repository.SecureStorageRepository

class ClearTokenUseCase(
    private val repo: SecureStorageRepository
) {
    suspend operator fun invoke() {
        repo.clearToken()
    }
}
