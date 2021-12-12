package com.example.beautystop.repositories

import android.content.Context
import com.example.beautystop.api.MakeupApi
import com.example.beautystop.models.WishlistModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


private const val BASE_URL = "http://makeup-api.herokuapp.com"
class ApiServiceRepository(val context: Context)  {

    private val retrofitService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val retrofitApi = retrofitService.create(MakeupApi::class.java)

    suspend fun getPhotos(product_type: String) =
        retrofitApi.getProducts(product_type)

    suspend fun getWishlist(id:String) = retrofitApi.getWishlist()
    suspend fun addToWishlist(wishlistBody:WishlistModel) = retrofitApi.addToWishlist(wishlistBody)
    suspend fun deleteFromWishlist(id:String) = retrofitApi.deleteFromWishlist(id)
    suspend fun editWishlist(id:String,wishlistBody: WishlistModel) = retrofitApi.editWishlist(id,wishlistBody)


    // to initialize and get the repository we use the companion object
    //singleton (single object)
    companion object {
        private var instance: ApiServiceRepository? = null

        fun init(context: Context) {
            if (instance == null)
                instance = ApiServiceRepository(context)
        }

        fun get(): ApiServiceRepository {
            return instance ?: throw  Exception("ApiServiceRepository must be initialized")
        }
    }


}