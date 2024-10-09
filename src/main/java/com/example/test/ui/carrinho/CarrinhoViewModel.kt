package com.example.test.ui.carrinho

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CarrinhoViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = ""  // Ou pode deixar sem valor
    }
    val text: LiveData<String> = _text
}
