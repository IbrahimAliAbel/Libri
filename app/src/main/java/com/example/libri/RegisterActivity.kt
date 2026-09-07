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

class RegisterActivity : AppCompatActivity() {

    private lateinit var editTextName: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonRegister: Button
    private lateinit var textViewLogin: TextView

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        editTextName = findViewById(R.id.editTextName)
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonRegister = findViewById(R.id.buttonRegister)
        textViewLogin = findViewById(R.id.textViewLogin)

        buttonRegister.setOnClickListener {
            register()
        }

        textViewLogin.setOnClickListener {
            startActivity(
                Intent(this, LoginActivity::class.java)
            )
            finish()
        }

        observeViewModel()
    }

    private fun register() {
        val name = editTextName.text.toString().trim()
        val email = editTextEmail.text.toString().trim()
        val password = editTextPassword.text.toString()

        if (name.isEmpty()) {
            editTextName.error = "Name is required"
            return
        }

        if (email.isEmpty()) {
            editTextEmail.error = "Email is required"
            return
        }

        if (password.isEmpty()) {
            editTextPassword.error = "Password is required"
            return
        }

        viewModel.register(
            name = name,
            email = email,
            password = password
        )
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                launch {
                    viewModel.registerResult.collect { user ->
                        if (user != null) {
                            Toast.makeText(
                                this@RegisterActivity,
                                "Registration successful",
                                Toast.LENGTH_SHORT
                            ).show()

                            startActivity(
                                Intent(
                                    this@RegisterActivity,
                                    LoginActivity::class.java
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
                                this@RegisterActivity,
                                errorMessage,
                                Toast.LENGTH_LONG
                            ).show()

                            viewModel.clearError()
                        }
                    }
                }

                launch {
                    viewModel.loading.collect { isLoading ->
                        buttonRegister.isEnabled = !isLoading

                        buttonRegister.text =
                            if (isLoading) "Registering..."
                            else "REGISTER"
                    }
                }
            }
        }
    }
}