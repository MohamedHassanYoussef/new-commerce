package com.example.commerceapp.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AddCoupon (
    @SerializedName("price_rule")
    val priceRule: PriceRule1
) : Serializable

data class PriceRule1 (
    @SerializedName("title")
    val title: String,

    @SerializedName("value_type")
    val valueType: String,

    @SerializedName("value")
    val value: String,

    @SerializedName("customer_selection")
    val customerSelection: String,

    @SerializedName("target_type")
    val targetType: String,

    @SerializedName("target_selection")
    val targetSelection: String,

    @SerializedName("allocation_method")
    val allocationMethod: String,

    @SerializedName("starts_at")
    val startsAt: String,

    @SerializedName("prerequisite_collection_ids")
    val prerequisiteCollectionIDS: List<Long>,

    @SerializedName("entitled_product_ids")
    val entitledProductIDS: List<Long>,

    @SerializedName("prerequisite_to_entitlement_quantity_ratio")
    val prerequisiteToEntitlementQuantityRatio: PrerequisiteToEntitlementQuantityRatio1,

    @SerializedName("allocation_limit")
    val allocationLimit: Long
) : Serializable

data class PrerequisiteToEntitlementQuantityRatio1 (
    @SerializedName("prerequisite_quantity")
    val prerequisiteQuantity: Long,

    @SerializedName("entitled_quantity")
    val entitledQuantity: Long
) : Serializable
