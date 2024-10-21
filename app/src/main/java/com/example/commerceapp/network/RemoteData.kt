package com.example.commerceapp.network

import UpdateProduct
import com.example.commerceapp.model.AddCoupon
import com.example.commerceapp.model.AddProduct
import com.example.commerceapp.model.DiscountCode
import com.example.commerceapp.model.DiscountList
import com.example.commerceapp.model.PriceRule
import com.example.commerceapp.model.PriceRuleList
import com.example.commerceapp.model.ProductCount
import com.example.commerceapp.model.ProductElement1
import com.example.commerceapp.model.Products
import com.example.commerceapp.model.ProductList
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Path

interface RemoteData {

    suspend fun getCountOfProducts(): Response<ProductCount?>





    // product
    suspend fun getAllProduct(): ProductList
    suspend fun updateProduct(productId: Long, product: UpdateProduct)
    suspend fun deleteProduct(productId: Long)
    suspend fun addProduct( addProduct: AddProduct)

    //coupon
    suspend fun getAllCoupon(): PriceRuleList
    suspend fun deleteCoupon(coupon: Long) : Response<Void?>
    suspend fun addCoupon(coupon: AddCoupon)
}

