package com.example.beautystop.models


import com.google.gson.annotations.SerializedName

data class WishlistModel(
    @SerializedName("image")
    val image: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("quantity")
    var quantity: Int,
    @SerializedName("id")
    val id: String,
    @SerializedName("userId")
    val userId: String,
    @SerializedName("price")
    val price: String,


)