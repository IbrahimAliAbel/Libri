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

class AdminLoginActivity : AppCompatActivity() {

    private lateinit var editTextAdminEmail: EditText
    private lateinit var editTextAdminPassword: EditText
    private lateinit var buttonAdminLogin: Button
    private lateinit var textViewBackToLogin: TextView

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_login)

        editTextAdminEmail = findViewById(R.id.editTextAdminEmail)
        editTextAdminPassword = findViewById(R.id.editTextAdminPassword)
        buttonAdminLogin = findViewById(R.id.buttonAdminLogin)
        textViewBackToLogin = findViewById(R.id.textViewBackToLogin)

        buttonAdminLogin.setOnClickListener {
            loginAsAdmin()
        }

        textViewBackToLogin.setOnClickListener {
            finish()
        }

        observeViewModel()
    }

    private fun loginAsAdmin() {
        val email = editTextAdminEmail.text.toString().trim()
        val password = editTextAdminPassword.text.toString()

        if (email.isEmpty()) {
            editTextAdminEmail.error = "Email is required"
            return
        }

        if (password.isEmpty()) {
            editTextAdminPassword.error = "Password is required"
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

                            if (user.role != "ADMIN") {
                                Toast.makeText(
                                    this@AdminLoginActivity,
                                    "This account is not an admin account",
                                    Toast.LENGTH_LONG
                                ).show()

                                return@collect
                            }

                            Toast.makeText(
                                this@AdminLoginActivity,
                                "Admin login successful",
                                Toast.LENGTH_SHORT
                            ).show()

                            startActivity(
                                Intent(
                                    this@AdminLoginActivity,
                                    AdminActivity::class.java
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
                                this@AdminLoginActivity,
                                errorMessage,
                                Toast.LENGTH_LONG
                            ).show()

                            viewModel.clearError()
                        }
                    }
                }

                launch {
                    viewModel.loading.collect { isLoading ->

                        buttonAdminLogin.isEnabled = !isLoading

                        buttonAdminLogin.text =
                            if (isLoading) {
                                "Logging in..."
                            } else {
                                "LOGIN AS ADMIN"
                            }
                    }
                }
            }
        }
    }
}