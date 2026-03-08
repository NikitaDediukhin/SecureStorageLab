package com.example.securestoragelab.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.securestoragelab.di.AppGraph
import com.example.securestoragelab.ui.MainScreen

class MainActivity : ComponentActivity() {

    private val graph by lazy { AppGraph(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val vm = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(
                    methodProvider = graph.methodProvider,
                    saveCredentials = graph.saveCredentialsUseCase,
                    loadCredentials = graph.loadCredentialsUseCase,
                    clearCredentials = graph.clearCredentialsUseCase,

                    saveToken = graph.saveTokenUseCase,
                    loadToken = graph.loadTokenUseCase,
                    clearToken = graph.clearTokenUseCase,

                    saveProfile = graph.saveProfileUseCase,
                    loadProfile = graph.loadProfileUseCase,
                    clearProfile = graph.clearProfileUseCase,

                    saveSettings = graph.saveSettingsUseCase,
                    loadSettings = graph.loadSettingsUseCase,
                    clearSettings = graph.clearSettingsUseCase,

                    saveLargeText = graph.saveLargeTextUseCase,
                    loadLargeText = graph.loadLargeTextUseCase,
                    clearLargeText = graph.clearLargeTextUseCase
                ) as T
            }
        })[MainViewModel::class.java]

        setContent { MainScreen(vm) }
    }
}