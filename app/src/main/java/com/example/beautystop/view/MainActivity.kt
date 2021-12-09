package com.example.beautystop.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.beautystop.R
import com.example.beautystop.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

//        setupActionBarWithNavController(navController)
        NavigationUI.setupWithNavController(binding.buttomNavigation,navController)
    }

    // If you want to back to the last fragment from where you come here just user the navigateUp method of NavController
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }


}