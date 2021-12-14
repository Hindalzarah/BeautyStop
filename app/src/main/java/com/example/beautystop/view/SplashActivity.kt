package com.example.beautystop.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import com.example.beautystop.R
//import com.example.beautystop.view.identity.LoginActivity
//import com.example.beautystop.view.identity.SHARED_PREF_FILE

class SplashActivity : AppCompatActivity() {

    private lateinit var sharedPref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)





        object: CountDownTimer(2000,1000) {
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                sharedPref = this@SplashActivity.getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE)
                if (sharedPref.getBoolean("isLogged?", false)){
                    val intent = Intent(this@SplashActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                    Log.d("splashpref", false.toString())

                }else {
                    val intent = Intent(this@SplashActivity, LoginActivity::class.java)

                    startActivity(intent)
                    finish()
                }

            }


        }.start()


    }
}