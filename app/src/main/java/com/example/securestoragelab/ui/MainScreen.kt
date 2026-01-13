package com.example.securestoragelab.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.securestoragelab.domain.model.StorageMethod
import com.example.securestoragelab.presentation.MainViewModel
import com.example.securestoragelab.presentation.Scenario

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MainScreen(vm: MainViewModel) {
    val state by vm.state.collectAsState()

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Spacer(modifier = Modifier.padding(vertical = 10.dp))
        // Storage method switch
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                selected = state.method == StorageMethod.SHARED_PREFS,
                onClick = { vm.onMethodChange(StorageMethod.SHARED_PREFS) },
                label = { Text("SharedPreferences") }
            )
            FilterChip(
                selected = state.method == StorageMethod.DATA_STORE,
                onClick = { vm.onMethodChange(StorageMethod.DATA_STORE) },
                label = { Text("DataStore") }
            )
            FilterChip(
                selected = state.method == StorageMethod.ENCRYPTED_SHARED_PREFS,
                onClick = { vm.onMethodChange(StorageMethod.ENCRYPTED_SHARED_PREFS) },
                label = { Text("EncSharedPrefs") }
            )
        }

        // Scenario switch
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            FilterChip(
                selected = state.scenario == Scenario.S1_CREDENTIALS,
                onClick = { vm.onScenarioChange(Scenario.S1_CREDENTIALS) },
                label = { Text("S1") }
            )
            FilterChip(
                selected = state.scenario == Scenario.S2_TOKEN,
                onClick = { vm.onScenarioChange(Scenario.S2_TOKEN) },
                label = { Text("S2") }
            )
            FilterChip(
                selected = state.scenario == Scenario.S3_PROFILE,
                onClick = { vm.onScenarioChange(Scenario.S3_PROFILE) },
                label = { Text("S3") }
            )
            FilterChip(
                selected = state.scenario == Scenario.S4_SETTINGS,
                onClick = { vm.onScenarioChange(Scenario.S4_SETTINGS) },
                label = { Text("S4") }
            )
        }

        // Fields by scenario
        when (state.scenario) {
            Scenario.S1_CREDENTIALS -> {
                OutlinedTextField(
                    value = state.login,
                    onValueChange = vm::onLoginChange,
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Login") }
                )
                OutlinedTextField(
                    value = state.password,
                    onValueChange = vm::onPasswordChange,
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Password") }
                )
            }

            Scenario.S2_TOKEN -> {
                OutlinedTextField(
                    value = state.token,
                    onValueChange = vm::onTokenChange,
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Token") }
                )
            }

            Scenario.S3_PROFILE -> {
                OutlinedTextField(
                    value = state.userId,
                    onValueChange = vm::onUserIdChange,
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("UserId") }
                )
                OutlinedTextField(
                    value = state.name,
                    onValueChange = vm::onNameChange,
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Name") }
                )
            }

            Scenario.S4_SETTINGS -> {
                OutlinedTextField(
                    value = state.theme,
                    onValueChange = vm::onThemeChange,
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Theme") }
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Biometrics")
                    Switch(
                        checked = state.biometrics,
                        onCheckedChange = vm::onBiometricsChange
                    )
                }
            }

            Scenario.S5_RESTORE -> {
                Text("S5 пока не реализован. Используй S1–S4.")
            }
        }

        // Actions
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(onClick = vm::onSaveClick) { Text("Save") }
            OutlinedButton(onClick = vm::onLoadClick) { Text("Load") }
            OutlinedButton(onClick = vm::onClearClick) { Text("Clear") }
        }

        // Result
        Text("Last read: ${state.lastRead}")

        if (state.status.isNotBlank()) {
            Text("Status: ${state.status}")
        }

        state.error?.let {
            Text("Error: $it")
        }
    }
}