package com.example.libri

import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.libri.viewmodel.BookViewModel
import kotlinx.coroutines.launch
import android.content.Intent
import android.widget.Button

class AdminBookActivity : AppCompatActivity() {

    private val viewModel: BookViewModel by viewModels()

    private lateinit var rvAdminBooks: RecyclerView
    private lateinit var tvBack: TextView
    private lateinit var adapter: AdminBookAdapter

    private lateinit var buttonAddBook: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_book)

        tvBack = findViewById(R.id.tvBack)
        rvAdminBooks = findViewById(R.id.rvAdminBooks)
        buttonAddBook = findViewById(R.id.buttonAddBook)

        buttonAddBook.setOnClickListener {
            startActivity(
                Intent(
                    this@AdminBookActivity,
                    AdminAddBookActivity::class.java
                )
            )
        }

        tvBack.setOnClickListener {
            finish()
        }

        adapter = AdminBookAdapter(emptyList()) { book ->
            startActivity(
                Intent(
                    this@AdminBookActivity,
                    AdminEditBookActivity::class.java
                ).apply {
                    putExtra("book_id", book.id)
                }
            )
        }

        rvAdminBooks.layoutManager = LinearLayoutManager(this)
        rvAdminBooks.adapter = adapter

        observeBooks()

        viewModel.loadBooks()
    }

    private fun observeBooks() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                viewModel.books.collect { books ->
                    adapter.updateData(books)
                }
            }
        }
    }
}