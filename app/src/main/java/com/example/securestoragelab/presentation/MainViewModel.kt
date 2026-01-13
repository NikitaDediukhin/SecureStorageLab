package com.example.securestoragelab.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.securestoragelab.domain.model.StorageMethod
import com.example.securestoragelab.domain.usecase.ReadValueUseCase
import com.example.securestoragelab.domain.usecase.WriteValueUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis

class MainViewModel(
    private val write: WriteValueUseCase,
    private val read: ReadValueUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(MainUiState())
    val state: StateFlow<MainUiState> = _state

    fun onMethodChange(method: StorageMethod) {
        _state.update { it.copy(method = method, status = "") }
    }

    fun onInputChange(text: String) {
        _state.update { it.copy(input = text) }
    }

    fun onWriteClick() {
        val s = _state.value
        viewModelScope.launch {
            val time = measureTimeMillis {
                write(s.method, s.input)
            }
            _state.update { it.copy(status = "Wrote in ${time}ms") }
        }
    }

    fun onReadClick() {
        val s = _state.value
        viewModelScope.launch {
            var value: String?
            val time = measureTimeMillis {
                value = read(s.method)
            }
            _state.update {
                it.copy(
                    lastRead = value ?: "null",
                    status = "Read in ${time}ms"
                )
            }
        }
    }
}