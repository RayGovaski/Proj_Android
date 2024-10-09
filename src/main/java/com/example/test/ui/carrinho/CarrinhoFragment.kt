package com.example.test.ui.carrinho

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.test.databinding.FragmentCarrinhoBinding

class CarrinhoFragment : Fragment() {

    private var _binding: FragmentCarrinhoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val carrinhoViewModel = ViewModelProvider(this).get(CarrinhoViewModel::class.java)
        _binding = FragmentCarrinhoBinding.inflate(inflater, container, false)

        // Inicialmente escondendo o TextView
        binding.textCarrinho.visibility = View.VISIBLE

        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
