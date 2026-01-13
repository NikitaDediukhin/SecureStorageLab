package com.example.securestoragelab.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.securestoragelab.domain.model.StorageMethod
import com.example.securestoragelab.presentation.MainViewModel

@Composable
fun MainScreen(vm: MainViewModel) {
    val state by vm.state.collectAsState()

    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {

        // Method switch
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
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
        }

        OutlinedTextField(
            value = state.input,
            onValueChange = vm::onInputChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Value to write") }
        )

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(onClick = vm::onWriteClick) { Text("Записать") }
            OutlinedButton(onClick = vm::onReadClick) { Text("Прочитать") }
        }

        Text("Last read: ${state.lastRead}")
        if (state.status.isNotBlank()) {
            Text("Status: ${state.status}")
        }
    }
}