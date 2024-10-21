package com.example.commerceapp.ui.editProduct.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.commerceapp.model.Repository


class EditProductFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(EditProductViewmodel::class.java)) {
            EditProductViewmodel(repository) as T
        } else {
            throw java.lang.IllegalArgumentException("ViewModel Class not found")
        }

    }
}