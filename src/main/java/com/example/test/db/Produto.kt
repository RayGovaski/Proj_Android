package com.example.test.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "produtos")
data class Produto(
   @PrimaryKey(autoGenerate = true)
   val id: Int = 0,
   val nome_produto: String,
   val imagem: String,
   val descricao: String,
   val preco: Double,
   val tipo_produto: String
)