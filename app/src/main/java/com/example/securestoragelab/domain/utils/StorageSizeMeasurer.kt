package com.example.securestoragelab.domain.utils

import com.example.securestoragelab.domain.model.StorageMethod

interface StorageSizeMeasurer {
    fun getCurrentSizeBytes(method: StorageMethod): Long
}