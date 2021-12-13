package com.example.beautystop.repositories

import android.content.Context
import com.example.beautystop.api.MakeupApi
import com.example.beautystop.models.WishlistModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



private const val BASE_URL = "https://61acf4abd228a9001703ac9f.mockapi.io"
class WishlistApiServiceRepository(val context: Context)  {


    private val retrofitService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val retrofitApi = retrofitService.create(MakeupApi::class.java)



    suspend fun getWishlist(id:String) = retrofitApi.getWishlist()
    suspend fun addToWishlist(wishlistBody: WishlistModel) = retrofitApi.addToWishlist(wishlistBody)
    suspend fun deleteFromWishlist(id:String) = retrofitApi.deleteFromWishlist(id)
    suspend fun editWishlist(id:String,wishlistBody: WishlistModel) = retrofitApi.editWishlist(id,wishlistBody)



    companion object {
        private var instance: WishlistApiServiceRepository? = null

        fun init(context: Context) {
            if (instance == null)
                instance = WishlistApiServiceRepository(context)
        }

        fun get(): WishlistApiServiceRepository {
            return instance ?: throw  Exception("ApiServiceRepository must be initialized")
        }
    }
}