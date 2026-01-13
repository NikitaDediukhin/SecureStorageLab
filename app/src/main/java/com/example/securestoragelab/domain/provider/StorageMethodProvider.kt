package com.example.securestoragelab.domain.provider

import com.example.securestoragelab.domain.model.StorageMethod

interface StorageMethodProvider {
    fun current(): StorageMethod
}