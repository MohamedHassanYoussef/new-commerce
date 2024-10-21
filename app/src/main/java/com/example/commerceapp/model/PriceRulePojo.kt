package com.example.commerceapp.model

import com.google.gson.annotations.SerializedName

data class PriceRuleList (
    @SerializedName("price_rules")
    val priceRules: List<PriceRule>
)

data class PriceRule (
    val id: Long,
    @SerializedName("value_type")
    val value_type: String? = "percentage",
    val value: String,
    @SerializedName("customer_selection")
    val customer_selection: String,
    @SerializedName("target_type")
    val target_type: String,
    @SerializedName("target_selection")
    val target_selection: String,
    @SerializedName("allocation_method")
    val allocation_method: String,
    @SerializedName("allocation_limit")
    val allocation_limit: Any? = null,
    @SerializedName("once_per_customer")
    val once_per_customer: Boolean,
    @SerializedName("usage_limit")
    val usage_limit: Any? = null,
    @SerializedName("starts_at")
    val starts_at: String,
    @SerializedName("ends_at")
    val ends_at: Any? = null,
    @SerializedName("created_at")
    val created_at: String?,
    @SerializedName("updated_at")
    val updatedAt: String?,
    @SerializedName("entitled_product_ids")
    val entitledProductIDS: List<Any?>,
    @SerializedName("entitled_variant_ids")
    val entitledVariantIDS: List<Any?>,
    @SerializedName("entitled_collection_ids")
    val entitledCollectionIDS: List<Any?>,
    @SerializedName("entitled_country_ids")
    val entitledCountryIDS: List<Any?>,
    @SerializedName("prerequisite_product_ids")
    val prerequisiteProductIDS: List<Any?>,
    @SerializedName("prerequisite_variant_ids")
    val prerequisiteVariantIDS: List<Any?>,
    @SerializedName("prerequisite_collection_ids")
    val prerequisiteCollectionIDS: List<Any?>,
    @SerializedName("customer_segment_prerequisite_ids")
    val customerSegmentPrerequisiteIDS: List<Any?>,
    @SerializedName("prerequisite_customer_ids")
    val prerequisiteCustomerIDS: List<Any?>,
    @SerializedName("prerequisite_subtotal_range")
    val prerequisiteSubtotalRange: Any? = null,
    @SerializedName("prerequisite_quantity_range")
    val prerequisiteQuantityRange: Any? = null,
    @SerializedName("prerequisite_shipping_price_range")
    val prerequisiteShippingPriceRange: Any? = null,
    @SerializedName("prerequisite_to_entitlement_quantity_ratio")
    val prerequisiteToEntitlementQuantityRatio: PrerequisiteToEntitlementQuantityRatio,
    @SerializedName("prerequisite_to_entitlement_purchase")
    val prerequisiteToEntitlementPurchase: PrerequisiteToEntitlementPurchase,
    val title: String,
    @SerializedName("admin_graphql_api_id")
    val adminGraphqlAPIID: String
)

data class PrerequisiteToEntitlementPurchase (
    @SerializedName("prerequisite_amount")
    val prerequisiteAmount: Any? = null
)

data class PrerequisiteToEntitlementQuantityRatio (
    @SerializedName("prerequisite_quantity")
    val prerequisiteQuantity: Any? = null,
    @SerializedName("entitled_quantity")
    val entitledQuantity: Any? = null
)
