package com.example.securestoragelab.data.utils

import android.content.Context
import android.util.Base64
import com.example.securestoragelab.domain.utils.CryptoEngine

class AeadCryptoEngine(
    private val context: Context
) : CryptoEngine {

    // заглушка
    override fun encrypt(plainText: String): String {
        return Base64.encodeToString(plainText.toByteArray(), Base64.DEFAULT)
    }

    override fun decrypt(cipherText: String): String {
        return String(Base64.decode(cipherText, Base64.DEFAULT))
    }
}