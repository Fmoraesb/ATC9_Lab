package com.fernandomoraes.atc9_sqlitelab

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener, AdapterView.OnItemClickListener
    , AdapterView.OnItemLongClickListener {

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

        val lstContatos : ListView = this.findViewById(R.id.lstContatos)
        lstContatos.onItemClickListener = this
        lstContatos.onItemLongClickListener = this
        //--------------------------------------------//
        val btnSalvar : Button = this.findViewById(R.id.btnSalvar)
        val btnExcluir : Button = this.findViewById(R.id.btnExcluir)
        btnSalvar.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
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
            val dialog = AlertDialog.Builder(this)
            dialog.setTitle("Editar Contato")
            dialog.setMessage("Deseja editar o contato ${contato.nome}?")
            dialog.setPositiveButton("Sim") { _: DialogInterface, _:Int ->
            contatoDao?.update(contato)
                listarContatos()
            }
            dialog.setNegativeButton("Não") { _: DialogInterface, _: Int ->
                this.idEmEdicao = 0
                txtNome.setText("")
                txtEmail.setText("")
                txtTelefone.setText("")
                listarContatos()
            }
            dialog.show()
        }

        //--------------------------------------------//
        this.idEmEdicao = 0
        txtNome.setText("")
        txtEmail.setText("")
        txtTelefone.setText("")
    }


    private fun listarContatos() {
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

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val id = this.listaDeIds[position]
        this.idEmEdicao = id
        val contato: Contato? = this.contatoDao?.retrieve(id)
        val txtNome : EditText = this.findViewById(R.id.edtNome)
        val txtEmail : EditText = this.findViewById(R.id.edtEmail)
        val txtTelefone: EditText = this.findViewById(R.id.edtTelefone)
        txtNome.setText(contato?.nome)
        txtEmail.setText(contato?.email)
        txtTelefone.setText(contato?.telefone)
        btnExcluir.isEnabled = true
    }

    override fun onItemLongClick(
        parent: AdapterView<*>?,
        view: View?,
        position: Int,
        id: Long
    ): Boolean {
       val confirmarExclusao = AlertDialog.Builder(this)
        confirmarExclusao.setTitle("Excluir Contato")
        confirmarExclusao.setMessage("Confirma a Exclusão do Contato ${contatoDao?.retrieve(id.toInt())}?")
        confirmarExclusao.setPositiveButton("Sim") { dialog: DialogInterface, _: Int ->
            val id = this.listaDeIds[position]
            this.contatoDao?.delete(id)
            listarContatos()
        }
        confirmarExclusao.setNegativeButton("Não") { dialog: DialogInterface?, _: Int ->

        }
        confirmarExclusao.show()
        return true
    }


}