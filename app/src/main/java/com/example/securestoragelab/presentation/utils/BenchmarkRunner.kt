package com.example.securestoragelab.presentation.utils

import android.os.SystemClock

data class BenchResult(
    val writeMs: List<Double>,
    val readMs: List<Double>
) {
    private fun median(list: List<Double>): Double {
        val s = list.sorted()
        return s[s.size / 2]
    }

    val writeMean: Double get() = writeMs.average()
    val readMean: Double get() = readMs.average()
    val writeMedian: Double get() = median(writeMs)
    val readMedian: Double get() = median(readMs)
}

object BenchmarkRunner {
    suspend fun <T> run(
        warmup: Int,
        runs: Int,
        clear: suspend () -> Unit,
        write: suspend () -> Unit,
        read: suspend () -> T,
        validate: suspend (T) -> Unit = {}
    ): BenchResult {

        repeat(warmup) {
            clear()
            write()
            val r = read()
            validate(r)
        }

        val writeTimes = ArrayList<Double>(runs)
        val readTimes = ArrayList<Double>(runs)

        repeat(runs) {
            clear()

            val t1 = SystemClock.elapsedRealtimeNanos()
            write()
            val t2 = SystemClock.elapsedRealtimeNanos()
            writeTimes += (t2 - t1) / 1_000_000.0

            val t3 = SystemClock.elapsedRealtimeNanos()
            val r = read()
            val t4 = SystemClock.elapsedRealtimeNanos()
            readTimes += (t4 - t3) / 1_000_000.0

            validate(r)
        }

        return BenchResult(writeTimes, readTimes)
    }
}