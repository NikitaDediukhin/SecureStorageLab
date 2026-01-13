package com.example.securestoragelab.domain.usecase

import com.example.securestoragelab.domain.model.Profile
import com.example.securestoragelab.domain.repository.SecureStorageRepository

class LoadProfileUseCase(
    private val repo: SecureStorageRepository
) {
    suspend operator fun invoke(): Profile? = repo.loadProfile()
}
