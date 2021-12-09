package com.example.beautystop.view.adapter

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beautystop.models.MakeupModel
import com.example.beautystop.repositories.ApiServiceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.log

private const val TAG = "ProductsListViewModel"
class ProductsListViewModel : ViewModel()  {

    private val apiRepo = ApiServiceRepository.get()

    val makeupProductsLiveData = MutableLiveData<List<MakeupModel>>()
    val makeupProductsErrorLiveData = MutableLiveData<String>()



    fun callMakeupProducts(type: String){
        viewModelScope.launch(Dispatchers.IO) {
            //trying to get the response from the api
            try{
                //call the api method to get the api response
                val response = apiRepo.getPhotos(type)
                //checking if the response is gotten and is successful
                if (response.isSuccessful){
                    Log.d(TAG, this.toString())

                    //using the livedata to pass the response to the view
                    response.body()?.run {
                        makeupProductsLiveData.postValue(this)
                    }



                }else{
                    Log.d(TAG,response.message())
                    makeupProductsErrorLiveData.postValue(response.message())
            }
        } catch (e: Exception){
            Log.d(TAG,e.message.toString())
                makeupProductsErrorLiveData.postValue(e.message.toString())
        }
        }


    }
}