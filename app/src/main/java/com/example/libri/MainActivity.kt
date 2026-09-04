package com.example.libri

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.libri.viewmodel.BookViewModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerViewBooks: RecyclerView
    private lateinit var adapter: BookCopyAdapter

    private val viewModel: BookViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerViewBooks = findViewById(R.id.recyclerViewBooks)

        adapter = BookCopyAdapter(emptyList())

        recyclerViewBooks.layoutManager = LinearLayoutManager(this)
        recyclerViewBooks.adapter = adapter

        observeBookCopies()
        observeError()

        viewModel.loadBookCopies()
    }

    private fun observeBookCopies() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.bookCopies.collect { bookCopies ->
                    adapter = BookCopyAdapter(bookCopies)
                    recyclerViewBooks.adapter = adapter
                }
            }
        }
    }

    private fun observeError() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.error.collect { errorMessage ->
                    if (errorMessage != null) {
                        android.util.Log.e("LIBRI_API", "Error: $errorMessage")
                    }
                }
            }
        }
    }
}