package com.example.commerceapp.ui.addcoupon.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.commerceapp.model.AddCoupon
import com.example.commerceapp.model.Repository
import com.example.commerceapp.network.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
class AddCouponViewModel(private val repository: Repository) : ViewModel() {

    private val _addCouponState = MutableStateFlow<State<Boolean>>(State.Loading)
    val addCouponState: StateFlow<State<Boolean>> = _addCouponState

    fun addCoupon(coupon: AddCoupon) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.addCoupon(coupon)
                _addCouponState.value = State.Success(true)
            } catch (e: Exception) {
                _addCouponState.value = State.Error(e)
                e.printStackTrace()
            }
        }
    }
}

