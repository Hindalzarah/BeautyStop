package com.example.beautystop.view

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.beautystop.R
import com.example.beautystop.databinding.ActivityRegisterBinding
import com.example.beautystop.databinding.FragmentCategoryMainBinding
import com.example.beautystop.util.RegistrationValidation
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val validator = RegistrationValidation()
    lateinit var emailContainer: TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)

        setContentView(binding.root)
        emailFocusListener()
        passwordFocusListener()

        binding.registerButton.setOnClickListener { submitForm() }

        val emailAddress: TextInputEditText = findViewById(R.id.emailAddress_EditText)
        val password: EditText = findViewById(R.id.password_EditText)
        val registerButton: Button = findViewById(R.id.register_button)
        val loginTextView: TextView = findViewById(R.id.login_TextView)
        emailContainer = findViewById(R.id.emailContainer)


        loginTextView.setOnClickListener() {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        registerButton.setOnClickListener() {
            val email: String = emailAddress.text.toString()
            val password: String = password.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {

                if (validator.emailIsValid(email)) {
                    if (validator.passwordIsValid(password)) {

                        FirebaseAuth.getInstance()
                            .createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    val firebaseuser: FirebaseUser = task.result!!.user!!
                                    Toast.makeText(this,
                                        "Registered Successfully",
                                        Toast.LENGTH_SHORT).apply {
                                        setGravity(
                                            Gravity.TOP, 0, 0); show()
                                    }

                                    val intent = Intent(this, LoginActivity::class.java)
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
                    } else
                        Toast.makeText(this,
                            "Make sure your password is strong.",
                            Toast.LENGTH_SHORT)
                            .apply { setGravity(Gravity.TOP, 0, 0); show() }
                } else{
                    binding.emailContainer.helperText = getString(R.string.required)
                binding.passwordContainer.helperText = getString(R.string.required)
                    Toast.makeText(this,
                        "Make sure you typed your email address correctly.",
                        Toast.LENGTH_SHORT)
                        .apply { setGravity(Gravity.TOP, 0, 0); show() } }
            }
        }
    }

    private fun submitForm() {
        binding.emailContainer.helperText = validEmail()
        binding.passwordContainer.helperText = validPassword()


        val validEmail = binding.emailContainer.helperText == null
        val validPassword = binding.passwordContainer.helperText == null

        if (validEmail && validPassword) resetForm() else invalidForm()
    }

    private fun invalidForm() {
        var message = ""
        if (binding.emailContainer.helperText != null)
            message += "\n\nEmail: " + binding.emailContainer.helperText
        if (binding.passwordContainer.helperText != null)
            message += "\n\nPassword: " + binding.passwordContainer.helperText

        AlertDialog.Builder(this)
            .setTitle("Invalid Form")
            .setMessage(message)
            .setPositiveButton("Okay") { _, _ ->
                // there's nothing to do here
            }.show()
    }

    private fun resetForm() {
        var message = "Email: " + binding.emailAddressEditText.text
        message += "\nPassword: " + binding.passwordEditText.text

        AlertDialog.Builder(this)
            .setTitle("Form submitted")
            .setMessage(message)
            .setPositiveButton("Okay") { _, _ ->
                binding.emailAddressEditText.text = null
                binding.passwordEditText.text = null
                binding.emailContainer.helperText = getString(R.string.required)
                binding.passwordContainer.helperText = getString(R.string.required)
            }.show()
    }

    private fun emailFocusListener() {
        binding.emailAddressEditText.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                emailContainer.helperText = validEmail()
            }
        }
    }

    private fun validEmail(): String? {

        val emailText = binding.emailAddressEditText.text.toString()
        if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            return "Invalid Email Address"
        }
        return null
    }

    private fun passwordFocusListener() {
        binding.passwordEditText.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.passwordContainer.helperText = validPassword()
            }
        }
    }

    private fun validPassword(): String? {

        val passwordText = binding.passwordEditText.text.toString()

        if (passwordText.length > 8) {
            return "Password must have at least 8 Characters"
        }
        if (!passwordText.matches(".*[a-z].*".toRegex())) {
            return "Password must contain at least one Lower-Case Character"
        }
        if (!passwordText.matches(".*[@#\$%^&+=].*".toRegex())) {
            return "Password must contain at least one special Character (@#\$%^&+=)"
        }
        if (!passwordText.matches(".*[A-Z].*".toRegex())) {
            return "Password must contain at least one Upper-Case Character"
        }
        return null
    }
}