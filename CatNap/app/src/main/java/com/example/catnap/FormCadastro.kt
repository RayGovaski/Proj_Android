package com.example.catnap

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
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
    private lateinit var confirma_senha: EditText;
    private lateinit var cadastrar_user: Button;
    lateinit var dbHelper: UserDatabase;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_form_cadastro)

        nome = findViewById(R.id.nome_edit)
        telefone = findViewById(R.id.telefone_edit)
        email = findViewById(R.id.email_edit)
        data_nasc = findViewById(R.id.data_nasc_edit)
        senha = findViewById(R.id.senha_edit)
        confirma_senha = findViewById(R.id.confirma_senha_edit)


        dbHelper = UserDatabase(this)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        IniciarComponentes()

        cadastrar_user.setOnClickListener {

            val userName = nome.text.toString()
            val userPhone = telefone.text.toString()
            val userBirthdate = data_nasc.text.toString()
            val userEmail = email.text.toString()
            val userPassword = senha.text.toString()
            val confirmPassword = confirma_senha.text.toString()

            if (userName.isNotEmpty() && userPhone.isNotEmpty() && userBirthdate.isNotEmpty()
                && userEmail.isNotEmpty() && userPassword.isNotEmpty()) {

                val isValidEmail = dbHelper.checkEmail(userEmail)

                if(isValidEmail){
                    Toast.makeText(this, "Email já cadastrado!", Toast.LENGTH_SHORT).show()
                } else{
                    if (confirmPassword == userPassword){

                        val insertResult = dbHelper.insertUser(userName, userPhone, userBirthdate, userEmail, userPassword)

                        if (insertResult) {
                            Toast.makeText(this, "Usuário cadastrado com sucesso!", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@FormCadastro, FormLogin::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this, "Erro ao cadastrar o usuário!", Toast.LENGTH_SHORT).show()
                        }
                    } else{
                        Toast.makeText(this, "Senhas divergentes!", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
            }

        }

        text_tela_login.setOnClickListener {
            val intent = Intent(this@FormCadastro, FormLogin::class.java)
            startActivity(intent)
        }

    }

    private fun IniciarComponentes() {
        text_tela_login = findViewById<TextView>(R.id.texto_login)
        cadastrar_user = findViewById<AppCompatButton>(R.id.confirma_button)
    }

}