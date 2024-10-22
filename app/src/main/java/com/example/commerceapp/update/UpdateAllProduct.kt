package com.example.commerceapp.update



data class Product1(
    val id: Long, // معرّف المنتج
    val title: String, // اسم المنتج
    val bodyHTML: String, // وصف المنتج بتنسيق HTML
    val vendor: String, // الشركة المصنعة
    val productType: String, // نوع المنتج
    val variants: List<Variant>, // قائمة المتغيرات
    val options: List<Option>, // قائمة الخيارات
    val images: List<Image1> // قائمة الصور
)

data class Variant(
    val id: Long, // معرّف المتغير
    val price: String, // سعر المتغير
    val option2: String // قيمة الخيار الثاني (مثل اللون)
)

data class Option(
    val id: Long, // معرّف الخيار
    val productId: Long, // معرّف المنتج الذي ينتمي إليه الخيار
    val name: String, // اسم الخيار (مثل "Size" أو "Color")
    val position: Int, // موقع الخيار في القائمة
    val values: List<String> // القيم المتاحة للخيار
)

data class Image1(
    val id: Long, // معرّف الصورة
    val src: String // رابط الصورة
)

