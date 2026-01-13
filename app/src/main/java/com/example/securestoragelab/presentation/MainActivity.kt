package com.example.securestoragelab.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.securestoragelab.di.AppGraph
import com.example.securestoragelab.ui.MainScreen
import com.example.securestoragelab.ui.theme.SecureStorageLabTheme

class MainActivity : ComponentActivity() {

    private val graph by lazy { AppGraph(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val vm = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(
                    write = graph.writeValueUseCase,
                    read = graph.readValueUseCase
                ) as T
            }
        })[MainViewModel::class.java]

        setContent {
            MainScreen(vm)
        }
    }
}