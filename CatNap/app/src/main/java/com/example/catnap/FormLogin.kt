package com.example.catnap

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class FormLogin : AppCompatActivity() {

    private lateinit var text_tela_cadastro: TextView;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_form_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        IniciarComponentes()

        text_tela_cadastro.setOnClickListener {
            val intent = Intent(this@FormLogin, FormCadastro::class.java)
            startActivity(intent)
        }

    }

    private fun IniciarComponentes() {
        text_tela_cadastro = findViewById<TextView>(R.id.text_cadastro)
    }

}