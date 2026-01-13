package com.example.securestoragelab.di

import android.content.Context
import com.example.securestoragelab.data.datastore.DataStoreStorage
import com.example.securestoragelab.data.repository.StorageRepositoryImpl
import com.example.securestoragelab.data.sharedprefs.SharedPrefsStorage
import com.example.securestoragelab.domain.repository.StorageRepository
import com.example.securestoragelab.domain.usecase.ReadValueUseCase
import com.example.securestoragelab.domain.usecase.WriteValueUseCase

class AppGraph(context: Context) {

    private val appContext = context.applicationContext

    private val spStorage = SharedPrefsStorage(appContext)
    private val dsStorage = DataStoreStorage(appContext)

    val repository: StorageRepository = StorageRepositoryImpl(
        sharedPrefs = spStorage,
        dataStore = dsStorage
    )

    val writeValueUseCase = WriteValueUseCase(repository)
    val readValueUseCase = ReadValueUseCase(repository)
}