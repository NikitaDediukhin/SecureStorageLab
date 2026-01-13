package com.example.securestoragelab.domain.usecase

import com.example.securestoragelab.domain.model.Profile
import com.example.securestoragelab.domain.repository.SecureStorageRepository

class SaveProfileUseCase(
    private val repo: SecureStorageRepository
) {
    suspend operator fun invoke(profile: Profile) {
        repo.saveProfile(profile)
    }
}
