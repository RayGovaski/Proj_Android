package com.example.test

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.room.Room
import com.example.test.db.Carrinho
import com.example.test.db.MIGRATION_1_2
import com.example.test.db.NapCatDataBase
import com.example.test.db.Produto
import com.example.test.ui.carrinho.CarrinhoFragment
import com.example.test.ui.home.HomeFragment
import com.example.test.ui.perfil.PerfilFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var db: NapCatDataBase
    private lateinit var itensCarrinhoLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializa o banco de dados com migração
        db = Room.databaseBuilder(
            applicationContext,
            NapCatDataBase::class.java, "cafeteria-db"
        ).addMigrations(MIGRATION_1_2).build()

        // Exemplo de inserção de produto e item no carrinho
        val produto = Produto(
            nome_produto = "Expresso",
            imagem = "url_da_imagem",
            descricao = "Um café expresso delicioso",
            preco = 5.50,
            tipo_produto = "Bebida"
        )

        val itemCarrinho = Carrinho(
            produtoId = 1, // ID do produto que foi inserido anteriormente
            quantidade = 2
        )

        // Insere o produto e o item no carrinho
        CoroutineScope(Dispatchers.IO).launch {
            db.productDao().inserirProduto(produto)
            db.carrinhoDao().inserirItem(itemCarrinho)
        }

        val navView: BottomNavigationView = findViewById(R.id.bottom_navigation)

        navView.setOnItemSelectedListener { item ->
            var selectedFragment: Fragment? = null
            when (item.itemId) {
                R.id.navigation_home -> selectedFragment = HomeFragment()
                R.id.navigation_dashboard -> selectedFragment = CarrinhoFragment()
                R.id.navigation_notifications -> selectedFragment = PerfilFragment()
            }
            if (selectedFragment != null) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment, selectedFragment)
                    .addToBackStack(null) // Opcional: Adiciona à pilha para navegação
                    .commit()
            }
            true
        }
        // Carregar o fragment inicial
        if (savedInstanceState == null) {
            navView.selectedItemId = R.id.navigation_home // Carrega o HomeFragment por padrão
        }

        itensCarrinhoLayout = findViewById(R.id.itensCarrinhoLayout)

        val botaoAdd = findViewById<Button>(R.id.botao_add)

        botaoAdd.setOnClickListener {
            val produtoId = 1 // ID do produto a ser adicionado ao carrinho
            val quantidade = 1

            CoroutineScope(Dispatchers.IO).launch {
                val itemExistente = db.carrinhoDao().getItemByProdutoId(produtoId)

                if (itemExistente == null) {
                    // Se o item não existe, insere um novo item no carrinho
                    val novoItem = Carrinho(produtoId = produtoId, quantidade = quantidade)
                    db.carrinhoDao().inserirItem(novoItem)
                } else {
                    // Se o item já existe, atualiza a quantidade
                    val itemAtualizado = itemExistente.copy(quantidade = itemExistente.quantidade + 1)
                    db.carrinhoDao().atualizarItem(itemAtualizado)
                }

                // Atualiza o layout do carrinho
                withContext(Dispatchers.Main) {
                    atualizarLayoutCarrinho()
                }
            }
        }

    }
    private fun atualizarLayoutCarrinho() {
        CoroutineScope(Dispatchers.IO).launch {
            val itensCarrinho = db.carrinhoDao().getTodosItensCarrinho()

            withContext(Dispatchers.Main) {
                itensCarrinhoLayout.removeAllViews()

                for (item in itensCarrinho) {
                    val produto = db.productDao().getProdutoById(item.produtoId)

                    val itemView = layoutInflater.inflate(R.layout.item_carrinho, itensCarrinhoLayout, false)

                    val produtoNome = itemView.findViewById<TextView>(R.id.carrinho_nome)
                    val produtoPreco = itemView.findViewById<TextView>(R.id.carrinho_preco)
                    val produtoImagem = itemView.findViewById<ImageView>(R.id.carrinho_imagem)

                    produtoNome.text = produto?.nome_produto ?: "Nome não disponível"
                    produtoPreco.text = getString(R.string.preco_produto, (produto?.preco ?: 0.0) * item.quantidade)

                    // Carregar imagem com Glide (ou outra biblioteca)
                    produtoImagem.setImageResource(R.drawable.limitada)

                    itensCarrinhoLayout.addView(itemView)
                }
            }
        }
    }




}











