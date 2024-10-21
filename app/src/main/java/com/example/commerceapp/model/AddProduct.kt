package com.example.commerceapp.model

import com.google.gson.annotations.SerializedName

data class AddProduct(
    val product: Product
)

data class Product(
    val title: String,
    @SerializedName("body_html")
    val bodyHTML: String,
    val vendor: String,
    @SerializedName("product_type")
    val productType: String,
    val tags: String,
    val variants: List<VariantAddProduct>,
    val images: List<ImageAdd?>
)

data class VariantAddProduct(
    @SerializedName("option1")
    val option1: String,
    val price: String,
    val sku: String
)

data class ImageAdd (
    val src: String?,
    val alt: String?
)
