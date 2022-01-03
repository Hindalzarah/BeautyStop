package com.example.beautystop.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.beautystop.R

import com.google.firebase.auth.FirebaseAuth


private lateinit var sharedPref: SharedPreferences
private lateinit var sharedPrefEditor: SharedPreferences.Editor
var SHARED_PREF_FILE = "preference"
private const val TAG = "LoginActivity"

class LoginActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val emailAdress: EditText = findViewById(R.id.login_email_edittext)
        val password: EditText = findViewById(R.id.login_password_edittext)
        val loginButton: Button = findViewById(R.id.login_button)
        val registerTextView: TextView = findViewById(R.id.register_textview)

        registerTextView.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()

        }

        sharedPref = this.getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE)
        if (sharedPref.getBoolean("is Logged", false)) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        loginButton.setOnClickListener {
            val email: String = emailAdress.text.toString()
            val password: String = password.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "User Logged in Successfully", Toast.LENGTH_SHORT)
                                .show()
                            // Navigate to Main Activity
                            val intent = Intent(this, MainActivity::class.java)
                            intent.putExtra("UserId", FirebaseAuth.getInstance().currentUser!!.uid)
                            intent.putExtra("Email", FirebaseAuth.getInstance().currentUser!!.email)
                            sharedPref =
                                this.getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE)
                            sharedPrefEditor = sharedPref.edit()
                            sharedPrefEditor.putBoolean("isLogged?", true)
                            sharedPrefEditor.commit()
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this,
                                task.exception!!.message.toString(),
                                Toast.LENGTH_SHORT).show()
                        }

                    }
            }
        }
    }
}
