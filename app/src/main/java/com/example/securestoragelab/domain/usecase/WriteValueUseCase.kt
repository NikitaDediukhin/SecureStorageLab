package com.example.securestoragelab.domain.usecase

import com.example.securestoragelab.domain.model.StorageMethod
import com.example.securestoragelab.domain.repository.StorageRepository

class WriteValueUseCase(
    private val repository: StorageRepository
) {
    suspend operator fun invoke(method: StorageMethod, value: String) {
        repository.write(method, value)
    }
}