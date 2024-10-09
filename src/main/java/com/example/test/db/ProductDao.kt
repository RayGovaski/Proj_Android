package com.example.test.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
public interface ProductDao {
    @Insert
    suspend fun inserirProduto(produto: Produto)

    @Update
    suspend fun atualizarProduto(produto: Produto)

    @Delete
    suspend fun deletarProduto(produto: Produto)

    @Query("SELECT * FROM produtos WHERE id = :produtoId")
    suspend fun getProdutoById(produtoId: Int): Produto?

}