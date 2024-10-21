package com.example.commerceapp.ui.coupouns.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.commerceapp.model.PriceRuleList
import com.example.commerceapp.model.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import com.example.commerceapp.network.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CouponViewModel(private val repository: Repository) : ViewModel() {

    private val _couponData = MutableStateFlow<State<PriceRuleList>>(State.Loading)
    val coupon: StateFlow<State<PriceRuleList>> = _couponData

    init {
        getAllCoupon()
    }

    fun getAllCoupon() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.getAllCoupon().collect { coupons ->
                    _couponData.value = State.Success(coupons)
                    Log.d("eeee2222", "CouponViewModel: $coupons")
                }
            } catch (e: Exception) {
                _couponData.value = State.Error(e)
                e.printStackTrace()
            }
        }

    }

    fun deleteCoupon(coupon: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteCoupon(coupon)
            getAllCoupon()
        }
    }


}