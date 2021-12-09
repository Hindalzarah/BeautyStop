package com.example.beautystop.models


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


data class MakeupModel(
    @SerializedName("api_featured_image")
    val apiFeaturedImage: String?,
    @SerializedName("brand")
    val brand: String?,
    @SerializedName("category")
    val category: String?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("currency")
    val currency: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("id")
    @PrimaryKey
    val id: Int,
    @SerializedName("image_link")
    val imageLink: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("price")
    val price: String?,
    @SerializedName("price_sign")
    val priceSign: String,
    @SerializedName("product_api_url")
    val productApiUrl: String?,
    @SerializedName("product_colors")
    val productColors: List<ProductColor>?,
    @SerializedName("product_link")
    val productLink: String?,
    @SerializedName("product_type")
    val productType: String?,
    @SerializedName("rating")
    val rating: Any?,
    @SerializedName("updated_at")
    val updatedAt: String?,
    @SerializedName("website_link")
    val websiteLink: String?,


    //created a variable to save the wishlist in
    val wishlist: List<MakeupModel>?
)