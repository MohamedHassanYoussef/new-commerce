package com.example.commerceapp.network


import android.util.Base64
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitHelper {
    private const val BASE_URL = "https://itp-ism-and2.myshopify.com/"

    private val onlineInterceptor: Interceptor = Interceptor { chain ->
        val response = chain.proceed(chain.request())
        val maxAge = 60
        response.newBuilder()
            .header("Cache-Control", "public, max-age=$maxAge")
            .removeHeader("Pragma")
            .build()
    }

    private val authInterceptor = Interceptor { chain ->
        val original = chain.request()
        val credentials = ""
        val authHeader = "Basic " + Base64.encodeToString(credentials.toByteArray(), Base64.NO_WRAP)

        val request = original.newBuilder()
            .header("Authorization", authHeader)
            .method(original.method, original.body)
            .build()
        chain.proceed(request)
    }


private val client: OkHttpClient = OkHttpClient.Builder()
    .addInterceptor(authInterceptor)
    .addNetworkInterceptor(onlineInterceptor)
    .build()

    private val retrofit1: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()





    val retrofit = retrofit1.create(ApiServeces::class.java)
}