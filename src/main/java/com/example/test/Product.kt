package com.example.test
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product(
    @PrimaryKey val uid: Int,
    val name: String,
    val imageResId: Int,
    val descricao: String,
    val preco: String
)