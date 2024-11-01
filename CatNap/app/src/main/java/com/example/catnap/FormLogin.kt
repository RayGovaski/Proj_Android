package com.example.catnap

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.catnap.database.ProductDatabase
import com.example.catnap.database.UserDatabase

class FormLogin : AppCompatActivity() {

    private lateinit var text_tela_cadastro: TextView
    private lateinit var dbHelper: UserDatabase
    private lateinit var dbHelper2: ProductDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_form_login)

        val login_email = findViewById<EditText>(R.id.Insert_email)
        val login_senha = findViewById<EditText>(R.id.Insert_senha)
        val button_login = findViewById<AppCompatButton>(R.id.Confirmar)

        dbHelper = UserDatabase(this)
        dbHelper2 = ProductDatabase(this)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        IniciarComponentes()

        button_login.setOnClickListener {
            val email = login_email.text.toString()
            val password = login_senha.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                val isValidUser = dbHelper.checkUser(email, password)

                if (isValidUser) {
                    Toast.makeText(this, "Login bem-sucedido!", Toast.LENGTH_SHORT).show()
                    val insert_product = dbHelper2.insertProduct("teste", "teste", "teste")
                    val intent2 = Intent(this@FormLogin, Pesquisa::class.java)
                    startActivity(intent2)
                    finish()
                } else {
                    Toast.makeText(this, "Email ou senha incorretos!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
            }
        }


        text_tela_cadastro.setOnClickListener {
            val intent = Intent(this@FormLogin, FormCadastro::class.java)
            startActivity(intent)
        }
    }

    private fun IniciarComponentes() {
        text_tela_cadastro = findViewById(R.id.text_cadastro)
    }
}
