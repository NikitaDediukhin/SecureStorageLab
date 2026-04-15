package com.example.securestoragelab.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.securestoragelab.domain.model.StorageMethod
import com.example.securestoragelab.presentation.MainViewModel
import com.example.securestoragelab.presentation.Scenario

@SuppressLint("DefaultLocale")
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
            FilterChip(
                selected = state.method == StorageMethod.SQLITE,
                onClick = { vm.onMethodChange(StorageMethod.SQLITE) },
                label = { Text("SQLite") }
            )
            FilterChip(
                selected = state.method == StorageMethod.SQLCIPHER,
                onClick = { vm.onMethodChange(StorageMethod.SQLCIPHER) },
                label = { Text("SQLCipher") }
            )
        }

        // Scenario switch
        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
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
            FilterChip(
                selected = state.scenario == Scenario.S5_LARGE_TEXT,
                onClick = { vm.onScenarioChange(Scenario.S5_LARGE_TEXT) },
                label = { Text("S5") }
            )
            FilterChip(
                selected = state.scenario == Scenario.S6_SIZE_BENCHMARK,
                onClick = { vm.onScenarioChange(Scenario.S6_SIZE_BENCHMARK) },
                label = { Text("S6") }
            )
            FilterChip(
                selected = state.scenario == Scenario.S7_COLD_START,
                onClick = { vm.onScenarioChange(Scenario.S7_COLD_START) },
                label = { Text("S7") }
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

            Scenario.S5_LARGE_TEXT -> {
                OutlinedButton(onClick = vm::onBenchmarkClick) { Text("Benchmark") }
            }

            Scenario.S6_SIZE_BENCHMARK -> {
                var keysInput by rememberSaveable { mutableStateOf("10") }

                OutlinedTextField(
                    value = keysInput,
                    onValueChange = { keysInput = it.filter(Char::isDigit) },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Keys count") }
                )

                Text("Value size: 2000 KB")

                Button(
                    onClick = {
                        val count = keysInput.toIntOrNull()
                        if (count != null) {
                            vm.runSizeBenchmark(count)
                        }
                    }
                ) {
                    Text("Run size benchmark")
                }

                Text(
                    text = "Current storage size: ${
                        String.format("%.2f", state.currentStorageSizeBytes / 1024.0)
                    } KB"
                )
            }

            Scenario.S7_COLD_START -> {
                var keysInput by rememberSaveable { mutableStateOf("10") }

                OutlinedTextField(
                    value = keysInput,
                    onValueChange = { keysInput = it.filter(Char::isDigit) },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Keys count") }
                )

                Text("Value size: 2000 KB")

                Button(
                    onClick = {
                        val count = keysInput.toIntOrNull()
                        if (count != null) {
                            vm.prepareColdStartData(count)
                        }
                    }
                ) {
                    Text("Prepare storage with data")
                }

                Button(
                    onClick = {
                        val count = keysInput.toIntOrNull()
                        if (count != null) {
                            vm.prepareColdWriteStorage()
                        }
                    }
                ) {
                    Text("Prepare storage")
                }

                Button(
                    onClick = {
                        val count = keysInput.toIntOrNull()
                        if (count != null) {
                            vm.runColdStartRead(count)
                        }
                    }
                ) {
                    Text("Run cold start test (read)")
                }

                Button(
                    onClick = {
                        val count = keysInput.toIntOrNull()
                        if (count != null) {
                            vm.runColdStartWrite(count)
                        }
                    }
                ) {
                    Text("Run cold start (write) test")
                }

                Text(
                    text = "Current storage size: ${
                        String.format("%.2f", state.currentStorageSizeBytes / 1024.0)
                    } KB"
                )
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