package com.example.securestoragelab.presentation.utils

object BenchmarkPayloadFactory {
    fun buildString(chars: Int): String {
        val sb = StringBuilder(chars)
        repeat(chars) { i ->
            sb.append(('a'.code + (i % 26)).toChar())
        }
        return sb.toString()
    }
}