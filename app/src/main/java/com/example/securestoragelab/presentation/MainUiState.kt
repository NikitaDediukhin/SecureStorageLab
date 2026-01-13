package com.example.securestoragelab.presentation

import com.example.securestoragelab.domain.model.StorageMethod

data class MainUiState(
    val method: StorageMethod = StorageMethod.SHARED_PREFS,
    val scenario: Scenario = Scenario.S1_CREDENTIALS,

    // S1
    val login: String = "user@example.com",
    val password: String = "P@ssw0rd123!",

    // S2
    val token: String = "eyJhbGciOiJIUzI1NiJFSb09kSb5",

    // S3
    val userId: String = "3f7a91c2...",
    val name: String = "Ivan",

    // S4
    val theme: String = "dark",
    val biometrics: Boolean = true,

    // Result
    val lastRead: String = "—",
    val status: String = "",
    val error: String? = null
)