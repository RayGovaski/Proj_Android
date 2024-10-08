package com.example.catnap

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.catnap.R
import com.squareup.picasso.Picasso
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import org.json.JSONObject
import java.io.IOException

class PagamentoActivity : AppCompatActivity() {

    private lateinit var valorPagamento: EditText
    private lateinit var btnPagar: Button
    private lateinit var qrCodeImageView: ImageView
    private val client = OkHttpClient()

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
        val url = "https://sandbox.api.pagseguro.com/orders" // Endpoint correto do PagBank
        val jsonBody = JSONObject().apply {
            put("reference_id", "ex-00001")
            put("customer", JSONObject().apply {
                put("name", "Jose da Silva")
                put("email", "email@test.com")
                put("tax_id", "12345678909")
                put("phones", listOf(JSONObject().apply {
                    put("country", "55")
                    put("area", "11")
                    put("number", "999999999")
                    put("type", "MOBILE")
                }))
            })
            put("items", listOf(JSONObject().apply {
                put("name", "nome do item")
                put("quantity", 1)
                put("unit_amount", valor.toInt())
            }))
            put("qr_codes", listOf(JSONObject().apply {
                put("amount", JSONObject().apply {
                    put("value", valor.toInt())
                })
                put("expiration_date", "2024-10-08T20:15:59-03:00") // Data de expiração
            }))
        }

        val requestBody = RequestBody.create(
            "application/json".toMediaTypeOrNull(), jsonBody.toString()
        )

        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .addHeader("Authorization", "Bearer 6d6d2da7-34c6-4ce6-9f17-904b82d3dedf2e1401aa4365b92dc2f01cfe612a91cf8341-d7c2-4271-a8bc-ae93ab112555") // Seu token de autenticação
            .addHeader("Content-Type", "application/json")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace() // Tratar o erro
            }

            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body?.string()
                if (response.isSuccessful && responseData != null) {
                    val json = JSONObject(responseData)
                    val qrCodeUrl = json.getJSONArray("qr_codes")
                        .getJSONObject(0).getJSONArray("links")
                        .getJSONObject(1).getString("href") // URL do QR Code em imagem PNG

                    runOnUiThread {
                        qrCodeImageView.visibility = ImageView.VISIBLE
                        Picasso.get().load(qrCodeUrl).into(qrCodeImageView) // Exibir o QR Code
                    }
                }
            }
        })
    }
}
