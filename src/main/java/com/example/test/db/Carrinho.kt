package com.example.test.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "carrinho")
data class Carrinho(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val produtoId: Int, // Referência ao ID do produto
    val quantidade: Int
)