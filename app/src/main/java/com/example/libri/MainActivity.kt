package com.example.libri

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerViewBooks: RecyclerView
    private lateinit var adapter: BookCopyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerViewBooks = findViewById(R.id.recyclerViewBooks)

        adapter = BookCopyAdapter(emptyList())

        recyclerViewBooks.layoutManager = LinearLayoutManager(this)
        recyclerViewBooks.adapter = adapter

        loadBookCopies()
    }

    private fun loadBookCopies() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val bookCopies = RetrofitClient.api.getBookCopies()

                Log.d("LIBRI_API", "Response: $bookCopies")

                withContext(Dispatchers.Main) {
                    adapter = BookCopyAdapter(bookCopies)
                    recyclerViewBooks.adapter = adapter
                }

            } catch (e: Exception) {
                Log.e("LIBRI_API", "Error: ${e.message}", e)
            }
        }
    }
}