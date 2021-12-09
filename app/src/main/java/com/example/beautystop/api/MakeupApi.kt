package com.example.beautystop.api

import com.example.beautystop.models.MakeupModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface MakeupApi {


    //this function gets a list of data from the api
    @GET("http://makeup-api.herokuapp.com/api/v1/products.json")
    suspend fun getProducts(
        @Query("product_type")  product_type: String
    ): Response<List<MakeupModel>>


//    @GET
//    suspend fun getWishlist(): Response<List<MakeupModel>>






}
