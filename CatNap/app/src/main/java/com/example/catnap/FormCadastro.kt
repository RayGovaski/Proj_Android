package com.example.catnap

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.catnap.database.UserDatabase

class FormCadastro : AppCompatActivity() {

    private lateinit var text_tela_login: TextView;
    private lateinit var nome: EditText;
    private lateinit var telefone: EditText;
    private lateinit var email: EditText;
    private lateinit var data_nasc: EditText;
    private lateinit var senha: EditText;
    private lateinit var cadastrar_user: Button;
    lateinit var dbHelper: UserDatabase;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_form_cadastro)

        dbHelper = UserDatabase(this)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        IniciarComponentes()

        cadastrar_user.setOnClickListener {

            val userName = nome.text.toString()

            val intent = Intent(this@FormCadastro, FormLogin::class.java)
            startActivity(intent)
        }

        text_tela_login.setOnClickListener {
            val intent = Intent(this@FormCadastro, FormLogin::class.java)
            startActivity(intent)
        }

    }

    private fun IniciarComponentes() {
        text_tela_login = findViewById<TextView>(R.id.texto_login)
        cadastrar_user = findViewById<Button>(R.id.confirma_button)
        nome = findViewById<EditText>(R.id.nome_edit)
        telefone = findViewById<EditText>(R.id.telefone_edit)
        data_nasc = findViewById<EditText>(R.id.data_nasc_edit)
        email = findViewById<EditText>(R.id.email_edit)
        senha = findViewById<EditText>(R.id.senha_edit)
    }

    private fun cadastrarUsuario(){
        val insertResult = dbHelper.insertUser(
            nome = "text"
        )

        if (insertResult) {
            Toast.makeText(this, "Usuário inserido com sucesso!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Erro ao inserir usuário!", Toast.LENGTH_SHORT).show()
        }

    }

}