package com.example.commerceapp.ui.addimage.viewmodel

import com.example.commerceapp.model.ProductList
import com.example.commerceapp.model.Repository
import com.example.commerceapp.network.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AddImageViewModel( private val repository: Repository) {

    private val _addImage =MutableStateFlow<State<ProductList>>(State.Loading)
    val addImage :StateFlow<State<ProductList>> =_addImage

}