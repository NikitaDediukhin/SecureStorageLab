package com.example.securestoragelab.presentation

import com.example.securestoragelab.domain.model.StorageMethod

data class MainUiState(
    val method: StorageMethod = StorageMethod.SHARED_PREFS,
    val input: String = "",
    val lastRead: String = "—",
    val status: String = ""
)