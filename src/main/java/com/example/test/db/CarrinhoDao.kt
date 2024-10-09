package com.example.test.db

import androidx.room.*

@Dao
interface CarrinhoDao {

    @Insert
    suspend fun inserirItem(carrinho: Carrinho): Long

    @Update
    suspend fun atualizarItem(carrinho: Carrinho)

    @Delete
    suspend fun deletarItem(carrinho: Carrinho)

    @Query("SELECT * FROM carrinho")
    suspend fun getTodosItensCarrinho(): List<Carrinho>

    @Query("SELECT * FROM carrinho WHERE produtoId = :produtoId")
    suspend fun getItemByProdutoId(produtoId: Int): Carrinho?

    @Query("DELETE FROM carrinho")
    suspend fun limparCarrinho()
}
