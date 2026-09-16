package com.example.libri

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class AdminActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        val buttonBorrowingRequests =
            findViewById<Button>(R.id.buttonBorrowingRequests)

        buttonBorrowingRequests.setOnClickListener {
            startActivity(
                Intent(
                    this@AdminActivity,
                    AdminBorrowingActivity::class.java
                )
            )
        }
    }
}