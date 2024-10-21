package com.example.commerceapp.ui.products.viewmodel

import UpdateProduct
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.commerceapp.model.ProductList
import com.example.commerceapp.model.Products
import com.example.commerceapp.model.Repository
import com.example.commerceapp.network.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ProductViewModel(private val repository: Repository) : ViewModel() {

    private val _productData = MutableStateFlow<State<ProductList>>(State.Loading)
    val productData: StateFlow<State<ProductList>> = _productData



    init {
        getProducts()
    }

    fun getProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.getAllProduct().collect { products ->
                    _productData.value = State.Success(products)
                }
            } catch (e: Exception) {
                _productData.value = State.Error(e)
                e.printStackTrace()
            }
        }
    }

    fun deleteProduct(productId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteProduct(productId)
            getProducts()
        }
    }





}
