package com.example.commerceapp.model

import UpdateProduct
import android.util.Log
import com.example.commerceapp.network.RemoteData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import retrofit2.Response

class RepositoryImplementation(private val remoteData: RemoteData) : Repository {

    companion object {
        private var instance: RepositoryImplementation? = null
        fun getInstance(remoteData: RemoteData): Repository {
            return instance ?: synchronized(this) {
                instance ?: RepositoryImplementation(remoteData)
                    .also { instance = it }
            }
        }
    }

    override suspend fun getCountOfProducts(): Flow<Response<ProductCount?>> {
        return flow {
            val productCount = remoteData.getCountOfProducts()
            emit(productCount)
        }
    }

    /*override suspend fun addProduct(
        thumb: String?,
        title: String?,
        brand: String?,
        price: Double?,
        vendor: String?,
        type: String?,
        description: String?
    ) {
        remoteData.addProduct(thumb, title, brand, price, vendor, type, description)
    }*/

    override suspend fun addProduct(addProduct: AddProduct) {
        remoteData.addProduct(addProduct)
    }
    override suspend fun deleteProduct(productId: Long) {
        remoteData.deleteProduct(productId)
    }

    override suspend fun getAllProduct(): Flow<ProductList> {
        return flowOf(remoteData.getAllProduct())
    }

    override suspend fun getAllCoupon(): Flow<PriceRuleList> {
        Log.d("eeee", "getAllCoupon: ${remoteData.getAllCoupon()}")
       return flowOf(remoteData.getAllCoupon())
    }

    override suspend fun deleteCoupon(coupon: Long) : Response<Void?> {
       return remoteData.deleteCoupon(coupon)
    }

    override suspend fun addCoupon(coupon: AddCoupon) {
        remoteData.addCoupon(coupon)
    }


    override suspend fun updateProduct(productId: Long, product: UpdateProduct) {
        remoteData.updateProduct(productId,product)
    }


}
