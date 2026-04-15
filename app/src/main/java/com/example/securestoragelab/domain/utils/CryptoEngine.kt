package com.example.securestoragelab.domain.utils

interface CryptoEngine {
    fun encrypt(plainText: String): String
    fun decrypt(cipherText: String): String
}