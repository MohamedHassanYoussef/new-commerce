package com.example.commerceapp.model

import UpdateProduct
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface Repository {
  /*  suspend fun addProduct(
        thumb: String?,
        title: String?,
        brand: String?,
        price: Double?,
        vendor: String?,
        type: String?,
        description: String?
    )*/



    suspend fun getCountOfProducts(): Flow<Response<ProductCount?>>




    suspend fun getAllProduct(): Flow<ProductList>
    suspend fun updateProduct(productId: Long, product: UpdateProduct)
    suspend fun deleteProduct(productId: Long)
    suspend fun addProduct( addProduct: AddProduct)


    suspend fun getAllCoupon(): Flow<PriceRuleList>
    suspend fun deleteCoupon(coupon: Long) : Response<Void?>
    suspend fun addCoupon(coupon: AddCoupon)


}
