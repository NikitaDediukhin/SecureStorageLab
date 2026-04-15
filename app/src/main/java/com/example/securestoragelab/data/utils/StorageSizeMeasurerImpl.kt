package com.example.securestoragelab.data.utils

import android.content.Context
import com.example.securestoragelab.domain.model.StorageMethod
import com.example.securestoragelab.domain.utils.StorageSizeMeasurer
import java.io.File

class StorageSizeMeasurerImpl(
    private val context: Context
): StorageSizeMeasurer {
    override fun getCurrentSizeBytes(method: StorageMethod): Long {
        return when (method) {
            StorageMethod.SHARED_PREFS -> {
                fileSizeInSharedPrefs("storage_bench")
            }
            StorageMethod.DATA_STORE -> {
                fileSizeInDataStore("storage_bench_ds")
            }
            StorageMethod.ENCRYPTED_SHARED_PREFS -> {
                fileSizeInSharedPrefs("storage_bench_encrypted")
            }
            StorageMethod.SQLITE -> {
                databaseTotalSize("storage_bench_plain.db")
            }
            StorageMethod.SQLCIPHER -> {
                databaseTotalSize("storage_bench_cipher.db")
            }
        }
    }

    private fun fileSizeInSharedPrefs(name: String): Long {
        val file = File(
            File(context.applicationInfo.dataDir, "shared_prefs"),
            "$name.xml"
        )
        return if (file.exists()) file.length() else 0L
    }

    private fun fileSizeInDataStore(fileName: String): Long {
        val file = File(
            File(context.filesDir, "datastore"),
            "$fileName.preferences_pb"
        )
        return if (file.exists()) file.length() else 0L
    }

    private fun databaseTotalSize(dbName: String): Long {
        val dbFile = context.getDatabasePath(dbName)
        val walFile = File(dbFile.absolutePath + "-wal")
        val shmFile = File(dbFile.absolutePath + "-shm")

        return listOf(dbFile, walFile, shmFile)
            .filter { it.exists() }
            .sumOf { it.length() }
    }
}