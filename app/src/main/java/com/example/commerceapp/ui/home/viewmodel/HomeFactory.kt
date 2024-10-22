package com.example.commerceapp.ui.home.viewmodel

import HomeViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.commerceapp.model.Repository


class HomeFactory (private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            HomeViewModel(repository) as T
        } else {
            throw java.lang.IllegalArgumentException("ViewModel Class not found")
        }

    }
}