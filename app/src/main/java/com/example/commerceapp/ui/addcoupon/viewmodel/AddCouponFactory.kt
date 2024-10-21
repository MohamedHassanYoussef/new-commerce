package com.example.commerceapp.ui.addcoupon.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.commerceapp.model.Repository


class AddCouponFactory (private val repository: Repository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(AddCouponViewModel::class.java)) {
            AddCouponViewModel(repository) as T
        } else {
            throw java.lang.IllegalArgumentException("ViewModel Class not found")
        }
    }

}