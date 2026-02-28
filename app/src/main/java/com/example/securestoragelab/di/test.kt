package com.example.securestoragelab.di

import kotlinx.coroutines.*

fun main() = runBlocking {
    var count = 0

    launch {
        println("start coroutine")
        count++
        println("stop coroutine")
    }

    println("count = $count")
}