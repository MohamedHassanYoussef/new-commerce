package com.example.commerceapp.network

sealed class State <out T>{
    data class Success<out T>(val data: T) : State<T>()
    data class Error(val message: Throwable) : State<Nothing>()
    object Loading : State<Nothing>()
}