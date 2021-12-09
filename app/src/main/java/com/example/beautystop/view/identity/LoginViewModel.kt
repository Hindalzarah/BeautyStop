package com.example.beautystop.view.identity

import androidx.lifecycle.MutableLiveData
import com.example.beautystop.repositories.ApiServiceRepository

private const val TAG = "LoginViewModel"
class LoginViewModel {

    private val apiRepo = ApiServiceRepository.get()

//    val loginLiveData = MutableLiveData<LoginModel>()
    val loginErrorLiveData = MutableLiveData<String>()

}