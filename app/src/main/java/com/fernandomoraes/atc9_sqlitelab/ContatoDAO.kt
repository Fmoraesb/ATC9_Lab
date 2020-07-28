package com.fernandomoraes.atc9_sqlitelab

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

class ContatoDAO(context: Context){
     val conexao: DBAdapter= DBAdapter(context)
     val banco: SQLiteDatabase? = conexao.conectar()

    fun create(contato: Contato) {
        val content: ContentValues = ContentValues()
        content.put("nome", contato.nome)
        content.put("email", contato.email)
        content.put("telefone", contato.telefone)
        banco?.insert("agenda", null, content)
    }

    fun retrieve(id: Int) : Contato? {
        var contato: Contato? = null
        val query = "SELECT * FROM agenda WHERE id = ?"
        val args = arrayOf(id.toString())
        val cursor: Cursor = banco!!.rawQuery(query, args)
        if (cursor.moveToFirst()) {
            var id = cursor.getInt(cursor.getColumnIndex("id"))
            var nome = cursor.getString(cursor.getColumnIndex("nome"))
            var email = cursor.getString(cursor.getColumnIndex("email"))
            var telefone = cursor.getString(cursor.getColumnIndex("telefone"))
            contato = Contato(id, nome, email, telefone)
        }
        return contato
    }

    fun update(contato: Contato) {
        val content = ContentValues()
        content.put("nome", contato.nome)
        content.put("email", contato.email)
        content.put("telefone", contato.telefone)
        banco?.update("agenda", content,"id=${contato.id}", null)
    }

    fun delete(id: Int) {
        banco?.delete("agenda", "id=${id}", null)
    }

    fun listar (): List<Contato> {
        var retorno: MutableList<Contato> = ArrayList()
        val query = "SELECT * FROM agenda ORDER BY nome"
        var cursor = banco?.rawQuery(query, null)
        var continua = cursor!!.moveToFirst()
        while (continua) {
            var id = cursor.getInt(cursor.getColumnIndex("id"))
            var nome = cursor.getString(cursor.getColumnIndex("nome"))
            var email = cursor.getString(cursor.getColumnIndex("email"))
            var telefone = cursor.getString(cursor.getColumnIndex("telefone"))
            var contato = Contato(id, nome, email, telefone)
            retorno.add(contato)
            continua = cursor.moveToNext()
        }
        return retorno
    }
}