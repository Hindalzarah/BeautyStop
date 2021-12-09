package com.example.beautystop.repositories

import com.example.beautystop.api.MakeupApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


private const val BASE_URL = "http://makeup-api.herokuapp.com"
class ApiServiceRepository {

    private val retrofitService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val retrofitApi = retrofitService.create(MakeupApi::class.java)

    suspend fun getPhotos() =
        retrofitApi.getProducts()



    // to initialize and get the repository we use the companion object
    //singleton (single object)
    companion object {
        private var instance: ApiServiceRepository? = null

        fun init() {
            if (instance == null)
                instance = ApiServiceRepository()
        }

        fun get(): ApiServiceRepository {
            return instance ?: throw  Exception("ApiServiceRepository must be initialized")
        }
    }


}