package com.example.beautystop.view

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beautystop.models.MakeupModel
import com.example.beautystop.models.ShoppingBagModel
import com.example.beautystop.repositories.WishlistApiServiceRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "DetailsViewModel"
class DetailsViewModel: ViewModel() {

    private val apiWish_ShoppingBag = WishlistApiServiceRepository.get()
    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
    val makeupProductsLiveData = MutableLiveData<List<MakeupModel>>()
    val makeupProductsErrorLiveData = MutableLiveData<String>()


    fun addToShoppingBag(shoppingBagModel: MakeupModel,quantity: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {

                val response = apiWish_ShoppingBag.addToShoppingCart(ShoppingBagModel(shoppingBagModel.imageLink!!,
                    shoppingBagModel.imageLink,
                    shoppingBagModel.name!!,
                    (shoppingBagModel.price!!.toInt()*quantity),quantity,userId))
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
