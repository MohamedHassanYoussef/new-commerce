package com.example.commerceapp.ui.editcoupon.viewmodel

import androidx.lifecycle.ViewModel
import com.example.commerceapp.model.DiscountList
import com.example.commerceapp.model.Repository
import com.example.commerceapp.network.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class EditCouponViewModel(private val repository: Repository): ViewModel() {
    private val _editCoupon =MutableStateFlow<State<DiscountList>>(State.Loading)
    val editCoupon : StateFlow<State<DiscountList>> = _editCoupon

}