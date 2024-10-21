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

class RemoteImplementation(private val apiService: ApiServeces) : RemoteData {

    companion object {
        private var instance: RemoteImplementation? = null
        fun getInstance(apiService: ApiServeces): RemoteImplementation {
            return instance ?: synchronized(this) {
                instance ?: RemoteImplementation(apiService)
                    .also { instance = it }
            }
        }
    }

    override suspend fun getCountOfProducts(): Response<ProductCount?> {
       return apiService.getCountOfProducts()
    }

//    override suspend fun addProduct(
//        thumb: String?,
//        title: String?,
//        brand: String?,
//        price: Double?,
//        vendor: String?,
//        type: String?,
//        description: String?
//    ) {
//        return apiService.addProduct(thumb, title, brand, price, vendor, type, description)
//    }

    override suspend fun addProduct(addProduct: AddProduct) {
        return apiService.addProduct(addProduct)
    }

    override suspend fun getAllProduct(): ProductList {
        return apiService.getAllProduct()
    }

    override suspend fun updateProduct(productId: Long, product: UpdateProduct) {
        return apiService.updateProduct(productId, product)
    }

    override suspend fun getAllCoupon(): PriceRuleList {
        return apiService.getAllCoupon()
    }



    override suspend fun deleteProduct(productId: Long) {
        return apiService.deleteProduct(productId)
    }

    override suspend fun deleteCoupon(coupon: Long) : Response<Void?> {
        return apiService.deleteCoupon(coupon)
    }

    override suspend fun addCoupon(coupon: AddCoupon) {
        return apiService.addCoupon(coupon)
    }
}
