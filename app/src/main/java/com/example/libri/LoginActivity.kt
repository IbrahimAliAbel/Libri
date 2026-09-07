package com.example.libri

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.libri.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonLogin: Button
    private lateinit var textViewRegister: TextView

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonLogin = findViewById(R.id.buttonLogin)
        textViewRegister = findViewById(R.id.textViewRegister)

        buttonLogin.setOnClickListener {
            login()
        }

        textViewRegister.setOnClickListener {
            startActivity(
                Intent(
                    this@LoginActivity,
                    RegisterActivity::class.java
                )
            )
        }

        observeViewModel()
    }

    private fun login() {
        val email = editTextEmail.text.toString().trim()
        val password = editTextPassword.text.toString()

        if (email.isEmpty()) {
            editTextEmail.error = "Email is required"
            return
        }

        if (password.isEmpty()) {
            editTextPassword.error = "Password is required"
            return
        }

        viewModel.login(email, password)
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                launch {
                    viewModel.loginResult.collect { user ->
                        if (user != null) {
                            Toast.makeText(
                                this@LoginActivity,
                                "Login successful",
                                Toast.LENGTH_SHORT
                            ).show()

                            startActivity(
                                Intent(
                                    this@LoginActivity,
                                    MainActivity::class.java
                                )
                            )

                            finish()
                        }
                    }
                }

                launch {
                    viewModel.error.collect { errorMessage ->
                        if (errorMessage != null) {
                            Toast.makeText(
                                this@LoginActivity,
                                errorMessage,
                                Toast.LENGTH_LONG
                            ).show()

                            viewModel.clearError()
                        }
                    }
                }

                launch {
                    viewModel.loading.collect { isLoading ->
                        buttonLogin.isEnabled = !isLoading

                        buttonLogin.text =
                            if (isLoading) "Logging in..."
                            else "LOGIN"
                    }
                }
            }
        }
    }
}