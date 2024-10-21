package com.example.commerceapp.ui.editProduct.viewmodel

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
import kotlinx.coroutines.launch

class EditProductViewmodel(private val repository: Repository) : ViewModel() {

    private val _editProduct = MutableStateFlow<State<ProductList>>(State.Loading)
    val editProduct: StateFlow<State<ProductList>> = _editProduct

    private val _productUpdateState = MutableStateFlow<State<UpdateProduct>>(State.Loading)
    val productUpdateState: StateFlow<State<UpdateProduct>> = _productUpdateState

    init {
        getProducts()
    }

    fun getProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.getAllProduct().collect { products ->
                    _editProduct.value = State.Success(products)
                }
            } catch (e: Exception) {
                _editProduct.value = State.Error(e)
                e.printStackTrace()
            }
        }
    }

    fun updateProductDetails(  products: UpdateProduct) {
        viewModelScope.launch {
            try {
                products.product.id?.let {
                    repository.updateProduct(it, products)
                    Log.d("updateProduct", "updateProductviewmodel:$it + $products ")
                    _productUpdateState.value = State.Success(products)
                    getProducts()
                }
            } catch (e: Exception) {
                _productUpdateState.value = State.Error(e)
                e.printStackTrace()
            }
        }
    }
}
