package com.example.commerceapp.model


import com.example.commerceapp.update.Option
import com.example.commerceapp.update.Variant
import com.google.gson.annotations.SerializedName

data class ProductList (
    @SerializedName("products")
    val products: List<Products>
)

data class ProductElement1(
    val thm: String?,
    val title: String?,
    val brand: String?,
    val price: Double?,
    val color: String?,
    val type: String?,
    val description: String?
)



data class Products (
    val id: Long,
    val title: String,
    @SerializedName("body_html") val bodyHtml: String,
    val vendor: String,
    @SerializedName("product_type") val productType: String,
    @SerializedName("created_at") val createdAt: String,
    val handle: String,
    @SerializedName("updated_at") val updatedAt: String,
    @SerializedName("published_at") val publishedAt: String,
    @SerializedName("template_suffix") val templateSuffix: String?,
    @SerializedName("published_scope") val publishedScope: String,
    val tags: String,
    val status: String,
    @SerializedName("admin_graphql_api_id") val adminGraphqlApiId: String,
    val variants: List<Variant>,
    val options: List<Option>,
    val images: List<Image>,
    val image: Image?
)

data class Image (
    val id: Long,
    val alt: String?,
    val position: Int,
    @SerializedName("product_id") val productId: Long,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String,
    @SerializedName("admin_graphql_api_id") val adminGraphqlApiId: String,
    val width: Int,
    val height: Int,
    val src: String,
    @SerializedName("variant_ids") val variantIds: List<Long>
)

data class Option (
    val id: Long,
    @SerializedName("product_id") val productId: Long,
    val name: String,
    val position: Int,
    val values: List<String>
)

enum class Name {
    Color,
    Size
}

enum class ProductType {
    Accessories,
    Shoes,
    TShirts
}

enum class PublishedScope {
    Global
}

enum class Status {
    Active
}

data class Variant (
    val id: Long,
    @SerializedName("product_id") val productId: Long,
    val title: String,
    val price: String,
    val position: Int,
    @SerializedName("inventory_policy") val inventoryPolicy: String,
    @SerializedName("compare_at_price") val compareAtPrice: String?,
    val option1: String,
    val option2: String? = null,
    val option3: String?,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String,
    val taxable: Boolean,
    val barcode: String?,
    @SerializedName("fulfillment_service") val fulfillmentService: String,
    val grams: Int,
    @SerializedName("inventory_management") val inventoryManagement: String,
    @SerializedName("requires_shipping") val requiresShipping: Boolean,
    val sku: String,
    val weight: Int,
    @SerializedName("weight_unit") val weightUnit: String,
    @SerializedName("inventory_item_id") val inventoryItemId: Long,
    @SerializedName("inventory_quantity") val inventoryQuantity: Int,
    @SerializedName("old_inventory_quantity") val oldInventoryQuantity: Int,
    @SerializedName("admin_graphql_api_id") val adminGraphqlApiId: String,
    @SerializedName("image_id") val imageId: Long?
)

enum class FulfillmentService {
    Manual
}

enum class InventoryManagement {
    Shopify
}

enum class InventoryPolicy {
    Deny
}

enum class Option2 {
    Beige,
    Black,
    Blue,
    Burgandy,
    Gray,
    LightBrown,
    Red,
    White,
    Yellow
}

enum class WeightUnit {
    Kg
}
enum class Currency(val symbol: String) {
    USD("$"),
    EGP("EGP")
}


data class ProductCount(

    @SerializedName("count")
    val count: Int = 0
)
