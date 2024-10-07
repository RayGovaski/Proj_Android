package com.example.catnap2;
import Product
import ProductAdapter
import SpaceItemDecoration
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProductAdapter
    private lateinit var productList: List<Product>
    private lateinit var filteredList: MutableList<Product>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val searchEditText = findViewById<EditText>(R.id.EditText)  // Pega a referência ao EditText

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 3) // 3 itens por linha

        // Criar lista de produtos
        productList = listOf(
            Product("Café com Leite", R.drawable.produto1, "Café com leite integral com amor felino", "10,00"),
            Product("Cappuccino", R.drawable.produto2, "Cappuccino com espuma cremosa e canela", "12,00"),
            Product("Espresso", R.drawable.produto3, "Espresso curto, forte e saboroso", "8,00"),
            Product("Macchiato", R.drawable.produto1, "Café espresso com uma pitada de leite", "9,00"),
            Product("Mocha", R.drawable.produto2, "Café com chocolate amargo e leite", "15,00"),
            Product("Latte", R.drawable.produto3, "Café espresso com bastante leite vaporizado", "11,00"),
            Product("Café Americano", R.drawable.produto1, "Café espresso diluído em água quente", "7,00"),
            Product("Café Gelado", R.drawable.produto2, "Café gelado com um toque de baunilha", "13,00"),
            Product("Chá Gelado", R.drawable.produto3, "Chá preto gelado com limão e hortelã", "10,00"),
            Product("Suco de Laranja", R.drawable.produto1, "Suco de laranja natural espremido na hora", "9,00"),
            Product("Croissant", R.drawable.produto2, "Croissant amanteigado, fresco e crocante", "6,00"),
            Product("Pão de Queijo", R.drawable.produto3, "Clássico pão de queijo mineiro quentinho", "5,00"),
            Product("Torrada com Manteiga", R.drawable.produto1, "Torrada francesa com manteiga derretida", "8,00"),
            Product("Bolo de Cenoura", R.drawable.produto2, "Bolo de cenoura com cobertura de chocolate", "7,00"),
            Product("Biscoito de Chocolate", R.drawable.produto3, "Biscoito de chocolate crocante", "4,00"),
            Product("Torta de Limão", R.drawable.produto1, "Torta de limão com base de biscoito crocante", "12,00"),
            Product("Muffin de Blueberry", R.drawable.produto2, "Muffin fofo com pedaços de blueberry", "9,00"),
            Product("Brownie", R.drawable.produto3, "Brownie de chocolate com nozes", "10,00"),
            Product("Sanduíche de Frango", R.drawable.produto1, "Sanduíche de frango grelhado com alface", "15,00"),
            Product("Salada de Frutas", R.drawable.produto2, "Salada de frutas frescas da estação", "8,00")
        )

        // Inicializar a lista filtrada
        filteredList = productList.toMutableList()

        // Configurar o Adapter com a lista filtrada e a função de callback
        adapter = ProductAdapter(filteredList) { product ->
            val intent = Intent(this, PagamentoActivity::class.java)
            // Passar informações do produto, se necessário
            intent.putExtra("PRODUCT_NAME", product.name)
            intent.putExtra("PRODUCT_PRICE", product.preco)
            startActivity(intent)
        }
        recyclerView.adapter = adapter

        // Adicionar ItemDecoration
        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.item_spacing) // Obtenha o espaço definido em dimensões
        recyclerView.addItemDecoration(SpaceItemDecoration(spacingInPixels))

        // Adicionar TextWatcher ao EditText para filtrar conforme o texto é digitado
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterProducts(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    // Função para filtrar os produtos com base no nome ou descrição
    private fun filterProducts(query: String) {
        val lowerCaseQuery = query.lowercase()

        filteredList.clear()

        for (product in productList) {
            if (product.name.lowercase().contains(lowerCaseQuery) ||
                product.descricao.lowercase().contains(lowerCaseQuery)) {
                filteredList.add(product)
            }
        }

        adapter.notifyDataSetChanged()  // Atualiza a RecyclerView
    }
}