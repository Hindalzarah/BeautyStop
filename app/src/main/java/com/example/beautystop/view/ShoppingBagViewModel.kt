package com.example.beautystop.view

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beautystop.models.ShoppingBagModel
import com.example.beautystop.models.WishlistModel
import com.example.beautystop.repositories.WishlistApiServiceRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

private const val TAG = "ShoppingBagViewModel"

class ShoppingBagViewModel: ViewModel() {

    val shoppingBagLiveData= MutableLiveData<List<ShoppingBagModel>>()
    val shoppingBagErrorLiveData = MutableLiveData<String>()
    val livedata = MutableLiveData<String>()
    val errorlivedata = MutableLiveData<String>()
    val apiRepo = WishlistApiServiceRepository.get()



    fun callShoppingBag() {
        viewModelScope.launch(Dispatchers.IO) {

            //sending request and trying to get a response
            try {

                //we need the user id to get the wishlist so we get the user id from the firebase
                val response = apiRepo.getShoppingBag(FirebaseAuth.getInstance().currentUser!!.uid)
                //if successful
                if (response.isSuccessful) {
                    Log.d(TAG,"in if condition")
                    Log.d(TAG,response.toString())

                    response.body()?.run {
                        //submitting the response in the livedata
                        shoppingBagLiveData.postValue(this.distinctBy { it.name })
                        Log.d(TAG,this.toString())
                    }
                } else {
                    //if unsuccessful
                    Log.d(TAG,response.toString())
                    Log.d(TAG,"in else condition")

                    Log.d(TAG,response.message())
                    //submitting the response in the error livedata
                    shoppingBagErrorLiveData.postValue(response.message())
                }

            } catch (e: Exception) {
                Log.d(TAG,"in catch")

                Log.d(TAG,e.message.toString())
                shoppingBagErrorLiveData.postValue(e.message.toString())
            }
        }



    }


    fun deleteFromShoppingBag(id:String){

        viewModelScope.launch(Dispatchers.IO) {

            try{
                val response = apiRepo.deleteFromShoppingBag(id)

                if (response.isSuccessful) {
                    livedata.postValue("successful")
                    Log.d(TAG,this.toString())

                } else {
                    Log.d(TAG,response.message())
                    errorlivedata.postValue(response.message())
                }
            } catch (e: Exception) {
                Log.d(TAG,e.message.toString())
                errorlivedata.postValue(e.message.toString())

            }
        }
    }



    fun editFromShoppingBag(shoppingbagmodel: ShoppingBagModel){

        viewModelScope.launch (Dispatchers.IO){

            Log.d(TAG,shoppingbagmodel.toString())
            try{
                val response = apiRepo.editShoppingBag(shoppingbagmodel.id,shoppingbagmodel)
                if (response.isSuccessful) {
                    livedata.postValue("successful")
                    Log.d(TAG,this.toString())

                } else {
                    Log.d(TAG,response.message())
                    errorlivedata.postValue(response.message())
                }
            } catch (e: Exception) {
                Log.d(TAG,e.message.toString())
                livedata.postValue(e.message.toString())

            }
        }
    }
}

