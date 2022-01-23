package com.example.beautystop.models


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


data class MakeupModel(

    @SerializedName("brand")
    val brand: String?,
    @SerializedName("id")
    @PrimaryKey
    val id: Int,
    @SerializedName("image_link")
    val imageLink: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("price")
    val price: String?,
    @SerializedName("product_link")
    val productLink: String?,




)