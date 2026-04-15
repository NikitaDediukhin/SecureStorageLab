package com.example.securestoragelab.di

import android.content.Context
import com.example.securestoragelab.data.datastore.DataStoreStorage
import com.example.securestoragelab.data.esharedprefs.EncryptedSharedPrefsStorage
import com.example.securestoragelab.data.provider.MutableStorageMethodProvider
import com.example.securestoragelab.data.repository.SecureStorageRepositoryImpl
import com.example.securestoragelab.data.sharedprefs.SharedPrefsStorage
import com.example.securestoragelab.data.sql.SQLiteStorage
import com.example.securestoragelab.data.sql.SqlCipherStorage
import com.example.securestoragelab.data.utils.AeadCryptoEngine
import com.example.securestoragelab.data.utils.StorageSizeMeasurerImpl
import com.example.securestoragelab.domain.model.StorageMethod
import com.example.securestoragelab.domain.repository.SecureStorageRepository
import com.example.securestoragelab.domain.usecase.ClearCredentialsUseCase
import com.example.securestoragelab.domain.usecase.ClearLargeTextUseCase
import com.example.securestoragelab.domain.usecase.ClearProfileUseCase
import com.example.securestoragelab.domain.usecase.ClearSettingsUseCase
import com.example.securestoragelab.domain.usecase.ClearTokenUseCase
import com.example.securestoragelab.domain.usecase.LoadCredentialsUseCase
import com.example.securestoragelab.domain.usecase.LoadLargeTextUseCase
import com.example.securestoragelab.domain.usecase.LoadProfileUseCase
import com.example.securestoragelab.domain.usecase.LoadSettingsUseCase
import com.example.securestoragelab.domain.usecase.LoadTokenUseCase
import com.example.securestoragelab.domain.usecase.SaveCredentialsUseCase
import com.example.securestoragelab.domain.usecase.SaveLargeTextUseCase
import com.example.securestoragelab.domain.usecase.SaveProfileUseCase
import com.example.securestoragelab.domain.usecase.SaveSettingsUseCase
import com.example.securestoragelab.domain.usecase.SaveTokenUseCase
import com.example.securestoragelab.domain.utils.CryptoEngine
import com.example.securestoragelab.domain.utils.StorageSizeMeasurer

class AppGraph(context: Context) {

    private val appContext = context.applicationContext

    // provider выбора метода
    val methodProvider = MutableStorageMethodProvider(StorageMethod.SHARED_PREFS)

    // шифрование
    private val cryptoEngine: CryptoEngine by lazy {
        AeadCryptoEngine(context)
    }

    // storage реализации
    private val spStorage = SharedPrefsStorage(appContext, cryptoEngine)
    private val dsStorage = DataStoreStorage(appContext)
    private val espStorage = EncryptedSharedPrefsStorage(appContext)
    private val sqliteStorage = SQLiteStorage(appContext)

    private val sqlCipherStorage =  SqlCipherStorage(
        context = appContext,
        passphrase = "lab-passphrase-123".toCharArray()
    )

    private val storages = mapOf(
        StorageMethod.SHARED_PREFS to spStorage,
        StorageMethod.DATA_STORE to dsStorage,
        StorageMethod.ENCRYPTED_SHARED_PREFS to espStorage,
        StorageMethod.SQLITE to sqliteStorage,
        StorageMethod.SQLCIPHER to sqlCipherStorage
    )
    val secureRepo: SecureStorageRepository =
        SecureStorageRepositoryImpl(storages = storages, methodProvider = methodProvider)

    // UseCases
    val saveCredentialsUseCase = SaveCredentialsUseCase(secureRepo)
    val loadCredentialsUseCase = LoadCredentialsUseCase(secureRepo)
    val clearCredentialsUseCase = ClearCredentialsUseCase(secureRepo)

    val saveTokenUseCase = SaveTokenUseCase(secureRepo)
    val loadTokenUseCase = LoadTokenUseCase(secureRepo)
    val clearTokenUseCase = ClearTokenUseCase(secureRepo)

    val saveProfileUseCase = SaveProfileUseCase(secureRepo)
    val loadProfileUseCase = LoadProfileUseCase(secureRepo)
    val clearProfileUseCase = ClearProfileUseCase(secureRepo)

    val saveSettingsUseCase = SaveSettingsUseCase(secureRepo)
    val loadSettingsUseCase = LoadSettingsUseCase(secureRepo)
    val clearSettingsUseCase = ClearSettingsUseCase(secureRepo)

    val saveLargeTextUseCase = SaveLargeTextUseCase(secureRepo)
    val loadLargeTextUseCase = LoadLargeTextUseCase(secureRepo)
    val clearLargeTextUseCase = ClearLargeTextUseCase(secureRepo)

    // utils
    val storageSizeMeasurer: StorageSizeMeasurer = StorageSizeMeasurerImpl(appContext)
}