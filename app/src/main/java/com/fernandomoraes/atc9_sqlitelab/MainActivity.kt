package com.fernandomoraes.atc9_sqlitelab

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView

class MainActivity : AppCompatActivity() {

    var contatoDao: ContatoDAO? = null
    var listaDeIds : ArrayList<Int> = ArrayList()
    var idEmEdicao = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //--------------------------------------------//
        this.contatoDao = ContatoDAO(this)
        listarContatos()
        //--------------------------------------------//
        val btnSalvar : Button = this.findViewById(R.id.btnSalvar)
        btnSalvar.setOnClickListener(this)
        //--------------------------------------------//
        val lstContatos : ListView = this.findViewById(R.id.lstContatos)
        lstContatos.setOnItemClickListener(this)
        lstContatos.setOnLongClickListener(this)
    }

    fun onClick(v: View?) {
        val txtNome : EditText = this.findViewById(R.id.edtNome)
        val txtEmail : EditText = this.findViewById(R.id.edtEmail)
        val txtTelefone: EditText = this.findViewById(R.id.edtTelefone)
        val contato : Contato = Contato(this.idEmEdicao
        ,txtNome.text.toString()
        ,txtEmail.text.toString()
        ,txtTelefone.text.toString())
        //--------------------------------------------//
        if (this.idEmEdicao == 0) {
            contatoDao?.create(contato)
        }else {
            contatoDao?.update(contato)
        }
        //--------------------------------------------//
        this.idEmEdicao = 0
        txtNome.setText("")
        txtEmail.setText("")
        txtTelefone.setText("")
        //--------------------------------------------//
        listarContatos()
    }

    fun listarContatos() {
        var contatos: List<Contato> = contatoDao!!.listar()
        var nomes: ArrayList<String> = ArrayList()
        this.listaDeIds = ArrayList()
        for (contato in contatos) {
            this.listaDeIds.add(contato.id)
            nomes.add(contato.nome)
        }
        val adapter : ArrayAdapter<String> = ArrayAdapter(this
            , android.R.layout.simple_list_item_1
            , nomes)
        val lstContatos : ListView = this.findViewById(R.id.lstContatos)
        lstContatos.adapter = adapter
    }

    onItemClick()
}