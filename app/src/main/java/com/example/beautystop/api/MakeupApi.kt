package com.example.beautystop.api

import com.example.beautystop.models.MakeupModel
import com.example.beautystop.models.WishlistModel
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface MakeupApi {


    //this function gets a list of data from the api
    @GET("/api/v1/products.json")
    suspend fun getProducts(
        @Query("product_type")  product_type: String
    ): Response<List<MakeupModel>>



    @GET("/wishlist")
    suspend fun getWishlist(): Response<List<WishlistModel>>

    //to add products to the wishlist fragment
    @POST("/wishlist")
    suspend fun addToWishlist( @Body Wishlistbody: WishlistModel): Response<ResponseBody>

    //to remove products from the wishlist fragment

    @DELETE("/wishlist/{id}")
    suspend fun deleteFromWishlist( @Path("id") Id: String): Response<ResponseBody>

    @PUT("/wishlist/{id}")
    suspend fun editWishlist( @Path("id") Id: String,
    @Body Wishlistbody: WishlistModel  ): Response<WishlistModel>












}
