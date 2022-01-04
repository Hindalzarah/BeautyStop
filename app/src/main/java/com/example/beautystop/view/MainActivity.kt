package com.example.beautystop.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.beautystop.R
import com.example.beautystop.databinding.ActivityMainBinding
import com.example.beautystop.repositories.ApiServiceRepository
import com.example.beautystop.repositories.WishlistApiServiceRepository
import org.imaginativeworld.oopsnointernet.callbacks.ConnectionCallback
import org.imaginativeworld.oopsnointernet.dialogs.signal.NoInternetDialogSignal

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    //    lateinit var connectionLiveData: ConnectionLiveData
    private var noInternetSnackbar: NoInternetDialogSignal? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        NoInternetDialogSignal.Builder(
            this,
            lifecycle
        ).apply {
            dialogProperties.apply {
                connectionCallback = object : ConnectionCallback { // Optional
                    override fun hasActiveConnection(hasActiveConnection: Boolean) {
                        // ...
                    }
                }

                cancelable = false
                noInternetConnectionTitle = "No Internet"
                noInternetConnectionMessage =
                    "Check your Internet connection and try again."
                showInternetOnButtons = true
                pleaseTurnOnText = "Please turn on"
                wifiOnButtonText = "Wifi"
                mobileDataOnButtonText = "Mobile data"

                onAirplaneModeTitle = "No Internet"
                onAirplaneModeMessage = "You have turned on the airplane mode."
                pleaseTurnOffText = "Please turn off"
                airplaneModeOffButtonText = "Airplane mode"
                showAirplaneModeOffButtons = true


            }
        }.build()


        // Initialize Api Service Repository only for one time with companion object function in ApiServiceRepository class
        ApiServiceRepository.init(this)
        WishlistApiServiceRepository.init(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        //the back button on the action bar
        setupActionBarWithNavController(navController)
        NavigationUI.setupWithNavController(binding.buttomNavigation, navController)
    }

    // If you want to get back to the last fragment you were in, use navigateUp method of NavController
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }


}





