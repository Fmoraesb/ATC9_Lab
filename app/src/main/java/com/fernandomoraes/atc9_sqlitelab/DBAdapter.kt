package com.fernandomoraes.atc9_sqlitelab

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBAdapter (context: Context?) : SQLiteOpenHelper(context, "agenda",null, 1 ) {

    fun conectar(): SQLiteDatabase? {
        return this.writableDatabase
    }

    fun desconectar() {
        this.close()
    }

    override fun onCreate(db: SQLiteDatabase?) {
       val sql = "CREATE TABLE agenda(id integer primary key autoincrement," +
               "nome text, email text, telefone text);"
        db?.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val sql = "DROP TABLE IF EXISTS agenda;"
        db?.execSQL(sql)
        onCreate(db)
    }
}