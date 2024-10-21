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
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServeces {

    //count
    @GET("products/count.json")
    suspend fun getCountOfProducts(): Response<ProductCount?>


//////////////////////////////////////////////////////////////////
    // product
    @GET("admin/api/2022-01/products.json")
    suspend fun getAllProduct(): ProductList

    @POST("admin/api/2024-10/products.json")
    suspend fun addProduct(@Body addProduct: AddProduct)

    @PUT("admin/api/2024-10/products/{product_id}.json")
    suspend fun updateProduct(@Path("product_id") productId: Long, @Body product: UpdateProduct)

    @DELETE("admin/api/2022-01/products/{id}.json")
    suspend fun deleteProduct(@Path("id") productId: Long)

/////////////////////////////////////////////////////////////////////////////////

    // coupon
   @GET("admin/api/2022-01/price_rules.json")
    suspend fun getAllCoupon(): PriceRuleList

    @DELETE("admin/api/2022-01/price_rules/{id}.json")
    suspend fun deleteCoupon(@Path("id") couponId: Long) : Response<Void?>

//    @POST("admin/api/2022-01/price_rules.json")
//    suspend fun addCoupon(@Body couponId: PriceRule)

    @POST("admin/api/2022-01/price_rules.json")
    suspend fun addCoupon(@Body addCoupon: AddCoupon)

}



