package com.example.commerceapp.ui.addproduct.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.commerceapp.model.AddProduct
import com.example.commerceapp.model.ProductElement1
import com.example.commerceapp.model.Repository
import com.example.commerceapp.network.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductDetailsViewModel(private val repository: Repository) : ViewModel() {

    private val _productAdditionState = MutableStateFlow<State<Boolean>>(State.Loading)
    val productAdditionState: StateFlow<State<Boolean>> = _productAdditionState

    fun addProduct(addProduct: AddProduct) {
        viewModelScope.launch(Dispatchers.IO) {
            try {

                repository.addProduct(addProduct)
                _productAdditionState.value = State.Success(true)
                Log.d("ProductDetailsViewModel", "Product added successfully.")
            } catch (e: Exception) {
                _productAdditionState.value = State.Error(e)
                Log.e("ProductDetailsViewModel", "Error adding product: ${e.message}")
            }
        }
    }
}
