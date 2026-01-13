package com.example.securestoragelab.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.securestoragelab.data.provider.MutableStorageMethodProvider
import com.example.securestoragelab.domain.model.Credentials
import com.example.securestoragelab.domain.model.Profile
import com.example.securestoragelab.domain.model.Settings
import com.example.securestoragelab.domain.model.StorageMethod
import com.example.securestoragelab.domain.usecase.ClearCredentialsUseCase
import com.example.securestoragelab.domain.usecase.ClearProfileUseCase
import com.example.securestoragelab.domain.usecase.ClearSettingsUseCase
import com.example.securestoragelab.domain.usecase.ClearTokenUseCase
import com.example.securestoragelab.domain.usecase.LoadCredentialsUseCase
import com.example.securestoragelab.domain.usecase.LoadProfileUseCase
import com.example.securestoragelab.domain.usecase.LoadSettingsUseCase
import com.example.securestoragelab.domain.usecase.LoadTokenUseCase
import com.example.securestoragelab.domain.usecase.SaveCredentialsUseCase
import com.example.securestoragelab.domain.usecase.SaveProfileUseCase
import com.example.securestoragelab.domain.usecase.SaveSettingsUseCase
import com.example.securestoragelab.domain.usecase.SaveTokenUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis

class MainViewModel(
    private val methodProvider: MutableStorageMethodProvider,

    private val saveCredentials: SaveCredentialsUseCase,
    private val loadCredentials: LoadCredentialsUseCase,
    private val clearCredentials: ClearCredentialsUseCase,

    private val saveToken: SaveTokenUseCase,
    private val loadToken: LoadTokenUseCase,
    private val clearToken: ClearTokenUseCase,

    private val saveProfile: SaveProfileUseCase,
    private val loadProfile: LoadProfileUseCase,
    private val clearProfile: ClearProfileUseCase,

    private val saveSettings: SaveSettingsUseCase,
    private val loadSettings: LoadSettingsUseCase,
    private val clearSettings: ClearSettingsUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(MainUiState())
    val state: StateFlow<MainUiState> = _state

    fun onMethodChange(method: StorageMethod) {
        methodProvider.set(method)
        _state.update { it.copy(method = method, status = "", error = null) }
    }

    fun onScenarioChange(scenario: Scenario) {
        _state.update { it.copy(scenario = scenario, status = "", error = null) }
    }

    fun onLoginChange(v: String) = _state.update { it.copy(login = v) }
    fun onPasswordChange(v: String) = _state.update { it.copy(password = v) }
    fun onTokenChange(v: String) = _state.update { it.copy(token = v) }
    fun onUserIdChange(v: String) = _state.update { it.copy(userId = v) }
    fun onNameChange(v: String) = _state.update { it.copy(name = v) }
    fun onThemeChange(v: String) = _state.update { it.copy(theme = v) }
    fun onBiometricsChange(v: Boolean) = _state.update { it.copy(biometrics = v) }

    fun onSaveClick() = runOp("Save") { s ->
        when (s.scenario) {
            Scenario.S1_CREDENTIALS ->
                saveCredentials(Credentials(s.login, s.password))

            Scenario.S2_TOKEN ->
                saveToken(s.token)

            Scenario.S3_PROFILE ->
                saveProfile(Profile(s.userId, s.name))

            Scenario.S4_SETTINGS ->
                saveSettings(Settings(s.theme, s.biometrics))

            Scenario.S5_RESTORE ->
                Unit
        }
    }

    fun onLoadClick() = runOp("Load") { s ->
        val text = when (s.scenario) {
            Scenario.S1_CREDENTIALS -> {
                val v = loadCredentials()
                v?.let { "login=${it.login}, password=${it.password}" } ?: "null"
            }
            Scenario.S2_TOKEN -> loadToken() ?: "null"
            Scenario.S3_PROFILE -> {
                val v = loadProfile()
                v?.let { "userId=${it.userId}, name=${it.name}" } ?: "null"
            }
            Scenario.S4_SETTINGS -> {
                val v = loadSettings()
                v?.let { "theme=${it.theme}, biometrics=${it.biometrics}" } ?: "null"
            }
            Scenario.S5_RESTORE -> "TODO: LoadAll"
        }
        _state.update { it.copy(lastRead = text) }
    }

    fun onClearClick() = runOp("Clear") { s ->
        when (s.scenario) {
            Scenario.S1_CREDENTIALS -> clearCredentials()
            Scenario.S2_TOKEN -> clearToken()
            Scenario.S3_PROFILE -> clearProfile()
            Scenario.S4_SETTINGS -> clearSettings()
            Scenario.S5_RESTORE -> Unit
        }
    }

    private fun runOp(name: String, block: suspend (MainUiState) -> Unit) {
        val snapshot = _state.value
        viewModelScope.launch {
            try {
                val time = measureTimeMillis { block(snapshot) }
                _state.update { it.copy(status = "$name in ${time}ms", error = null) }
            } catch (t: Throwable) {
                _state.update {
                    it.copy(
                        status = "$name failed",
                        error = t.message ?: t.toString()
                    )
                }
            }
        }
    }
}