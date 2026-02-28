package com.example.securestoragelab.data.sql

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.securestoragelab.data.repository.KeyValueStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SQLiteStorage(context: Context) : KeyValueStorage {

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
            // для лабораторной достаточно так
            db.execSQL("DROP TABLE IF EXISTS kv")
            onCreate(db)
        }
    }

    override suspend fun putString(key: String, value: String) = withContext(Dispatchers.IO) {
        val db = helper.writableDatabase
        db.execSQL(
            "INSERT OR REPLACE INTO kv(k,t,v_text,v_int) VALUES(?,?,?,NULL)",
            arrayOf(key, TYPE_STRING, value)
        )
    }

    override suspend fun getString(key: String): String? = withContext(Dispatchers.IO) {
        val db = helper.readableDatabase
        db.rawQuery("SELECT v_text FROM kv WHERE k=? AND t=?", arrayOf(key, TYPE_STRING.toString()))
            .use { c -> if (c.moveToFirst()) c.getString(0) else null }
    }

    override suspend fun putBoolean(key: String, value: Boolean) = withContext(Dispatchers.IO) {
        val db = helper.writableDatabase
        val intVal = if (value) 1 else 0
        db.execSQL(
            "INSERT OR REPLACE INTO kv(k,t,v_text,v_int) VALUES(?,?,NULL,?)",
            arrayOf(key, TYPE_BOOL, intVal)
        )
    }

    override suspend fun getBoolean(key: String): Boolean? = withContext(Dispatchers.IO) {
        val db = helper.readableDatabase
        db.rawQuery("SELECT v_int FROM kv WHERE k=? AND t=?", arrayOf(key, TYPE_BOOL.toString()))
            .use { c ->
                if (!c.moveToFirst()) return@withContext null
                c.getInt(0) != 0
            }
    }

    override suspend fun remove(key: String) = withContext(Dispatchers.IO) {
        val db = helper.writableDatabase
        db.execSQL("DELETE FROM kv WHERE k=?", arrayOf(key))
    }

    override suspend fun clearAll() = withContext(Dispatchers.IO) {
        val db = helper.writableDatabase
        db.execSQL("DELETE FROM kv")
    }

    private companion object {
        private const val DB_NAME = "storage_bench_plain.db"
        private const val DB_VERSION = 1
        private const val TYPE_STRING = 1
        private const val TYPE_BOOL = 2
    }
}