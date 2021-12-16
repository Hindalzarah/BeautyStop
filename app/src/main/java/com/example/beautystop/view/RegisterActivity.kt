package com.example.beautystop.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.beautystop.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val emailAddress: EditText = findViewById(R.id.emailAddress_EditText)
        val password: EditText = findViewById(R.id.password_EditText)
        val registerButton: Button = findViewById(R.id.register_button)
        val loginTextView: TextView = findViewById(R.id.login_TextView)


        loginTextView.setOnClickListener() {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        registerButton.setOnClickListener() {
            val email: String = emailAddress.text.toString()
            val password: String = password.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val firebaseuser: FirebaseUser = task.result!!.user!!
                            Toast.makeText(this, "Registered Successfully", Toast.LENGTH_SHORT)
                                .show()
                            val intent = Intent(this, MainActivity::class.java)
                            intent.putExtra("UserId", firebaseuser.uid)
                            intent.putExtra("Email", firebaseuser.email)
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