package com.example.commerceapp.ui.addimage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.commerceapp.model.Repository

class AddImageFactory(private val repository: Repository):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return  if (modelClass.isAssignableFrom(AddImageViewModel::class.java)) {
            AddImageViewModel(repository) as T
        }else{
            throw java.lang.IllegalArgumentException("ViewModel Class not found")

        }
    }

}