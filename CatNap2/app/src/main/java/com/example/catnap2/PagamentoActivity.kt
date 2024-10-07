package com.example.catnap2;
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.catnap2.R
import com.squareup.picasso.Picasso
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

class PagamentoActivity : AppCompatActivity() {

    private lateinit var valorPagamento: EditText
    private lateinit var btnPagar: Button
    private lateinit var qrCodeImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pagamento)

        valorPagamento = findViewById(R.id.valor_pagamento)
        btnPagar = findViewById(R.id.btn_pagar)
        qrCodeImageView = findViewById(R.id.qrcode_pix)

        btnPagar.setOnClickListener {
            val valor = valorPagamento.text.toString()
            if (valor.isNotEmpty()) {
                iniciarPagamentoPIX(valor)
            }
        }
    }

    private fun iniciarPagamentoPIX(valor: String) {
        // API de pagamento com PIX
        val url = "https://api.pagamento.com/pix/create" // Exemplo de endpoint

        val client = OkHttpClient()
        val requestBody = FormBody.Builder()
            .add("valor", valor)
            .build()

        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace() // Tratar o erro
            }

            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body?.string()
                if (response.isSuccessful && responseData != null) {
                    val json = JSONObject(responseData)
                    val qrCodeUrl = json.getString("qrcode_url") // O URL do QR Code gerado pela API

                    runOnUiThread {
                        // Exibir o QR Code gerado
                        qrCodeImageView.visibility = ImageView.VISIBLE
                        Picasso.get().load(qrCodeUrl).into(qrCodeImageView) // Picasso para carregar a imagem do QR Code
                    }
                }
            }
        })
    }
}
