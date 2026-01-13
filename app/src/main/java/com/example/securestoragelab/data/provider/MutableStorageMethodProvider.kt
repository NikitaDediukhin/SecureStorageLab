package com.example.securestoragelab.data.provider

import com.example.securestoragelab.domain.model.StorageMethod
import com.example.securestoragelab.domain.provider.StorageMethodProvider

class MutableStorageMethodProvider(
    initial: StorageMethod = StorageMethod.SHARED_PREFS
) : StorageMethodProvider {

    @Volatile
    private var selected: StorageMethod = initial

    override fun current(): StorageMethod = selected

    fun set(method: StorageMethod) {
        selected = method
    }
}