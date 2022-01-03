package com.example.beautystop.view

import android.util.Log
import androidx.lifecycle.LiveData
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
import java.lang.Exception


private const val TAG = "WishlistViewModel"
class WishlistViewModel: ViewModel() {

    private val apiRepo = WishlistApiServiceRepository.get()

    val id: String = ""
    val wishlistLiveData = MutableLiveData<List<WishlistModel>>()
    val wishlistErrorLiveData = MutableLiveData<String>()
    val deleteWishlistLiveData = MutableLiveData<String>()
    val editWishlistLiveData = MutableLiveData<String>()

    fun callWishlist() {
        viewModelScope.launch(Dispatchers.IO) {

            //sending request and trying to get a response
            try {
                Log.d(TAG,"in try")

                //we need the user id to get the wishlist so we get the user id from the firebase
                val response = apiRepo.getWishlist(FirebaseAuth.getInstance().currentUser!!.uid)
                //if sucessful
                if (response.isSuccessful) {
                    Log.d(TAG,"in if condition")
                    Log.d(TAG,response.toString())

                    response.body()?.run {
                        //submitting the response in the livedata
                        wishlistLiveData.postValue(this)
                        Log.d(TAG,this.toString())
                    }
                } else {
                    //if unsuccessful
                    Log.d(TAG,response.toString())
                    Log.d(TAG,"in else condition")

                    Log.d(TAG,response.message())
                    //submitting the response in the error livedata
                    wishlistErrorLiveData.postValue(response.message())
                }

            } catch (e: Exception) {
                Log.d(TAG,"in catch")

                Log.d(TAG,e.message.toString())
                wishlistErrorLiveData.postValue(e.message.toString())
            }
        }

    }


    fun deleteFromWishlist(id:String){

        viewModelScope.launch(Dispatchers.IO) {

            try{
                val response = apiRepo.deleteFromWishlist(id)

                if (response.isSuccessful) {
                        deleteWishlistLiveData.postValue("successful")
                        Log.d(TAG,this.toString())

                } else {
                    Log.d(TAG,response.message())
                    wishlistErrorLiveData.postValue(response.message())
                }
            } catch (e: Exception) {
                Log.d(TAG,e.message.toString())
                wishlistErrorLiveData.postValue(e.message.toString())

            }
        }
    }

    fun editFromWishlist(wishlistBody: WishlistModel){

        viewModelScope.launch (Dispatchers.IO){

            Log.d(TAG,wishlistBody.toString())
            try{
                val response = apiRepo.editWishlist(wishlistBody.id,wishlistBody)
                if (response.isSuccessful) {
                    editWishlistLiveData.postValue("successful")
                    Log.d(TAG,this.toString())

                } else {
                    Log.d(TAG,response.message())
                    wishlistErrorLiveData.postValue(response.message())
                }
            } catch (e: Exception) {
                Log.d(TAG,e.message.toString())
                wishlistErrorLiveData.postValue(e.message.toString())

            }
        }
    }
}







