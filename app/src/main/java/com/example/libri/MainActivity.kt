package com.example.libri

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.api.getBookCopies()

                Log.d("LIBRI_API", "Response: $response")

            } catch (e: Exception) {
                Log.e("LIBRI_API", "Error: ${e.message}", e)
            }
        }
    }
}