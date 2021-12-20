package com.example.beautystop.models


import com.google.gson.annotations.SerializedName

data class ShoppingBagModel(
    @SerializedName("id")
    val id: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("quantity")
    val quantity: Int,
    @SerializedName("userId")
    val userId: String
)