package com.example.securestoragelab.domain.usecase

import com.example.securestoragelab.domain.model.StorageMethod
import com.example.securestoragelab.domain.repository.StorageRepository

class ReadValueUseCase(
    private val repository: StorageRepository
) {
    suspend operator fun invoke(method: StorageMethod): String? =
        repository.read(method)
}