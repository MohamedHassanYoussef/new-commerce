package com.example.commerceapp.model


data class DiscountList(
    val disountList :List<DiscountCode>
)


data class DiscountCode(
    val id: Long,
    val price_rule_id: Long,
    val code: String,
    val usage_count: Int,
    val created_at: String,
    val updated_at: String
)

data class DiscountCodeResponse(
    val discount_code: DiscountCode
)
