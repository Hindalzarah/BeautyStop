package com.example.beautystop.repositories

import android.content.Context
import com.example.beautystop.api.MakeupApi
import com.example.beautystop.models.ShoppingBagModel
import com.example.beautystop.models.WishlistModel
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



private const val BASE_URL = "https://61acf4abd228a9001703ac9f.mockapi.io"
class WishlistApiServiceRepository(val context: Context)  {


    private val retrofitService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val retrofitApi = retrofitService.create(MakeupApi::class.java)



    suspend fun getWishlist(id:String) = retrofitApi.getWishlist(FirebaseAuth.getInstance().currentUser!!.uid)
    suspend fun addToWishlist(wishlistBody: WishlistModel) = retrofitApi.addToWishlist(wishlistBody)
    suspend fun deleteFromWishlist(id:String) = retrofitApi.deleteFromWishlist(id)
    suspend fun editWishlist(id:String,wishlistBody: WishlistModel) = retrofitApi.editWishlist(id,wishlistBody)


    //Shopping bag
    suspend fun getShoppingBag(id:String) = retrofitApi.getShoppingBag(FirebaseAuth.getInstance().currentUser!!.uid)
    suspend fun addToShoppingCart(shoppingBagBody: ShoppingBagModel) = retrofitApi.addToShoppingBag(shoppingBagBody)
    suspend fun deleteFromShoppingBag(id:String) = retrofitApi.deleteFromShoppingBag(id)
    suspend fun editShoppingBag(id:String,shoppingBagBody: ShoppingBagModel) = retrofitApi.editShoppingBag(id,shoppingBagBody)


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