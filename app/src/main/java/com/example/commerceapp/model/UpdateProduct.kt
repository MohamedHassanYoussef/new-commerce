import com.google.gson.annotations.SerializedName

data class UpdateProduct(
    val product: Product
)

data class Product(
    val id: Long? = null,
    val title: String,
    @SerializedName("body_html") val bodyHTML: String,
    val vendor: String,
    @SerializedName("product_type") val productType: String,
    val variants: List<Variant22>
)

data class Variant22(
    val price: String,
    @SerializedName("option_2") val option2: String? = null
)