package com.example.catnap

import com.example.catnap.database.ProductDatabase
import SpaceItemDecoration
import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.catnap.PagamentoActivity
import com.example.catnap.Product
import com.example.catnap.ProductAdapter
import com.example.catnap.database.UserDatabase


class Pesquisa : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProductAdapter // Corrigir o tipo aqui
    private lateinit var productList: MutableList<Product>
    private lateinit var filteredList: MutableList<Product>
    private lateinit var dbHelper: UserDatabase

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = UserDatabase(this)

        val searchEditText = findViewById<EditText>(R.id.EditText)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 3)

        // Carregar produtos do banco de dados
        productList = loadProductsFromDB()
        filteredList = productList.toMutableList()

        adapter = ProductAdapter(filteredList) { product ->
            val intent = Intent(this, PagamentoActivity::class.java)
            intent.putExtra("PRODUCT_NAME", product.name)
            intent.putExtra("PRODUCT_PRICE", product.preco)
            startActivity(intent)
        }
        recyclerView.adapter = adapter

        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.item_spacing)
        recyclerView.addItemDecoration(SpaceItemDecoration(spacingInPixels))

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterProducts(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    // Função para carregar os produtos do banco de dados
    @SuppressLint("Range")
    private fun loadProductsFromDB(): MutableList<Product> {
        val productList = mutableListOf<Product>()
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.query(
            ProductDatabase.TABLE_PRODUCTS, null, null, null, null, null, null
        )

        if (cursor.moveToFirst()) {
            do {
                val name = cursor.getString(cursor.getColumnIndex(ProductDatabase.COLUMN_PRODUCT_NAME))
                val descricao = cursor.getString(cursor.getColumnIndex(ProductDatabase.COLUMN_PRODUCT_DESC))
                val preco = cursor.getDouble(cursor.getColumnIndex(ProductDatabase.COLUMN_PRODUCT_PRICE)).toString()
                val imageResId = R.drawable.produto1 // Para simplificar, usar imagem padrão

                productList.add(Product(name, imageResId, descricao, preco))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return productList
    }

    private fun filterProducts(query: String) {
        val lowerCaseQuery = query.lowercase()
        filteredList.clear()

        for (product in productList) {
            if (product.name.lowercase().contains(lowerCaseQuery) ||
                product.descricao.lowercase().contains(lowerCaseQuery)) {
                filteredList.add(product)
            }
        }
    }
}
