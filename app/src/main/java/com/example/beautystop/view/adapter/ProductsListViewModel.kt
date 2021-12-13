package com.example.beautystop.view.adapter

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beautystop.models.MakeupModel
import com.example.beautystop.models.WishlistModel
import com.example.beautystop.repositories.ApiServiceRepository
import com.example.beautystop.repositories.WishlistApiServiceRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.log

private const val TAG = "ProductsListViewModel"

class ProductsListViewModel : ViewModel() {

    private val apiRepo = ApiServiceRepository.get()
    private val apiWish = WishlistApiServiceRepository.get()

    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
    val makeupProductsLiveData = MutableLiveData<List<MakeupModel>>()
    val makeupProductsErrorLiveData = MutableLiveData<String>()
    val addMakeupProductsLiveData = MutableLiveData<String>()

    //for when a specific item is selected to display the details fragment
    val selectItem = MutableLiveData<MakeupModel>()


    fun callMakeupProducts(type: String) {
        viewModelScope.launch(Dispatchers.IO) {
            //trying to get the response from the api
            try {
                //call the api method to get the api response
                val response = apiRepo.getPhotos(type)
                //checking if the response is gotten and is successful
                if (response.isSuccessful) {
                    Log.d(TAG, this.toString())

                    //using the livedata to pass the response to the view
                    response.body()?.run {
                        makeupProductsLiveData.postValue(this)
                    }


                } else {
                    Log.d(TAG, response.message())
                    makeupProductsErrorLiveData.postValue(response.message())
                }
            } catch (e: Exception) {
                Log.d(TAG, e.message.toString())
                makeupProductsErrorLiveData.postValue(e.message.toString())
            }
        }


    }


    fun addToWishlist(wishlistBody: MakeupModel){
        viewModelScope.launch(Dispatchers.IO) {
            try {

                val response = apiWish.addToWishlist(WishlistModel(wishlistBody.imageLink!!,wishlistBody.name!!,1,"",userId))
                if (response.isSuccessful) {


                    Log.d(TAG, this.toString())

                    //using the livedata to pass the response to the view


                } else {
                    Log.d(TAG, response.message())
                    makeupProductsErrorLiveData.postValue(response.message())
                }

            } catch (e: Exception) {

                    Log.d(TAG, e.message.toString())
                    makeupProductsErrorLiveData.postValue(e.message.toString())
            }
        }
    }
}