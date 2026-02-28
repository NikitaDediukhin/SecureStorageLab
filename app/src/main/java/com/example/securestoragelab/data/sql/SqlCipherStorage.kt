package com.example.securestoragelab.data.sql

import android.content.Context
import com.example.securestoragelab.data.repository.KeyValueStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SQLiteOpenHelper

class SqlCipherStorage(
    context: Context,
    private val passphrase: CharArray
) : KeyValueStorage {

    init {
        // важно: загрузка нативных либ SQLCipher
        SQLiteDatabase.loadLibs(context)
    }

    private val helper = object : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
        override fun onCreate(db: SQLiteDatabase) {
            db.execSQL(
                """
                CREATE TABLE IF NOT EXISTS kv(
                    k TEXT PRIMARY KEY,
                    t INTEGER NOT NULL,
                    v_text TEXT,
                    v_int INTEGER
                )
                """.trimIndent()
            )
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            db.execSQL("DROP TABLE IF EXISTS kv")
            onCreate(db)
        }
    }

    private fun writableDb(): SQLiteDatabase =
        helper.getWritableDatabase(String(passphrase))

    private fun readableDb(): SQLiteDatabase =
        helper.getReadableDatabase(String(passphrase))

    override suspend fun putString(key: String, value: String) = withContext(Dispatchers.IO) {
        writableDb().use { db ->
            db.execSQL(
                "INSERT OR REPLACE INTO kv(k,t,v_text,v_int) VALUES(?,?,?,NULL)",
                arrayOf(key, TYPE_STRING, value)
            )
        }
    }

    override suspend fun getString(key: String): String? = withContext(Dispatchers.IO) {
        readableDb().use { db ->
            db.rawQuery(
                "SELECT v_text FROM kv WHERE k=? AND t=?",
                arrayOf(key, TYPE_STRING.toString())
            ).use { c -> if (c.moveToFirst()) c.getString(0) else null }
        }
    }

    override suspend fun putBoolean(key: String, value: Boolean) = withContext(Dispatchers.IO) {
        val intVal = if (value) 1 else 0
        writableDb().use { db ->
            db.execSQL(
                "INSERT OR REPLACE INTO kv(k,t,v_text,v_int) VALUES(?,?,NULL,?)",
                arrayOf(key, TYPE_BOOL, intVal)
            )
        }
    }

    override suspend fun getBoolean(key: String): Boolean? = withContext(Dispatchers.IO) {
        readableDb().use { db ->
            db.rawQuery(
                "SELECT v_int FROM kv WHERE k=? AND t=?",
                arrayOf(key, TYPE_BOOL.toString())
            ).use { c ->
                if (!c.moveToFirst()) return@withContext null
                c.getInt(0) != 0
            }
        }
    }

    override suspend fun remove(key: String) = withContext(Dispatchers.IO) {
        writableDb().use { db ->
            db.execSQL("DELETE FROM kv WHERE k=?", arrayOf(key))
        }
    }

    override suspend fun clearAll() = withContext(Dispatchers.IO) {
        writableDb().use { db ->
            db.execSQL("DELETE FROM kv")
        }
    }

    private companion object {
        private const val DB_NAME = "storage_bench_cipher.db"
        private const val DB_VERSION = 1
        private const val TYPE_STRING = 1
        private const val TYPE_BOOL = 2
    }
}