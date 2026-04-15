package com.example.securestoragelab.data.utils

import android.content.Context
import android.util.Base64
import com.example.securestoragelab.domain.utils.CryptoEngine
import com.google.crypto.tink.Aead
import com.google.crypto.tink.KeyTemplates
import com.google.crypto.tink.aead.AeadConfig
import com.google.crypto.tink.integration.android.AndroidKeysetManager
import java.nio.charset.StandardCharsets

class AeadCryptoEngine(context: Context) : CryptoEngine {

    private val aead: Aead

    init {
        AeadConfig.register()

        val keysetHandle = AndroidKeysetManager.Builder()
            .withSharedPref(
                context,
                "secure_storage_keyset",
                "secure_storage_master_key_pref"
            )
            .withKeyTemplate(KeyTemplates.get("AES256_GCM"))
            .withMasterKeyUri("android-keystore://secure_storage_master_key")
            .build()
            .keysetHandle

        aead = keysetHandle.getPrimitive(Aead::class.java)
    }

    override fun encrypt(plainText: String): String {
        val cipherBytes = aead.encrypt(
            plainText.toByteArray(StandardCharsets.UTF_8),
            null
        )
        return Base64.encodeToString(cipherBytes, Base64.NO_WRAP)
    }

    override fun decrypt(cipherText: String): String {
        val cipherBytes = Base64.decode(cipherText, Base64.NO_WRAP)
        val plainBytes = aead.decrypt(cipherBytes, null)
        return String(plainBytes, StandardCharsets.UTF_8)
    }
}