package com.example.beautystop.view

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beautystop.models.MakeupModel
import com.example.beautystop.models.ShoppingBagModel
import com.example.beautystop.models.WishlistModel
import com.example.beautystop.repositories.ApiServiceRepository
import com.example.beautystop.repositories.WishlistApiServiceRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "ProductsListViewModel"

class ProductsListViewModel : ViewModel() {


    private val apiRepo = ApiServiceRepository.get()
    private val apiWish_ShoppingBag = WishlistApiServiceRepository.get()

    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
    val makeupProductsLiveData = MutableLiveData<List<MakeupModel>>()
    val makeupProductsErrorLiveData = MutableLiveData<String>()


    //paging lists
    var allList = listOf<MakeupModel>()
    var pagelist = mutableListOf<MakeupModel>()
    var currentPage = 0
    var limit = 15
    var pages = 0
//    var counter = 0

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
                        allList = this

                        pages = this.size / limit

                        for (item in 0 until limit) {
                            pagelist.add(allList[item])

                        }

                        currentPage++

                        makeupProductsLiveData.postValue(pagelist)
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


    fun addToWishlist(wishlistBody: MakeupModel) {
        viewModelScope.launch(Dispatchers.IO) {
            try {

                val response = apiWish_ShoppingBag.addToWishlist(WishlistModel(wishlistBody.imageLink!!,
                    wishlistBody.name!!,
                    1,
                    "",
                    userId,wishlistBody.price.toString()))
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


    fun searchBrand(brand: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = apiRepo.searchBrand(brand)
                if (response.isSuccessful) {
                    Log.d(TAG, this.toString())
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


    fun nextPage() {

        var startIndex = currentPage*limit
        var endIndex = (currentPage+1)*limit
        for(index in startIndex..endIndex){
            pagelist.add(allList[index])
        }

    }



}

