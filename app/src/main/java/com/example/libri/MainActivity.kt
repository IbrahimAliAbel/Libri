package com.example.libri

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.libri.viewmodel.BookViewModel
import kotlinx.coroutines.launch
import android.widget.EditText
import android.text.Editable
import android.text.TextWatcher

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerViewBooks: RecyclerView
    private lateinit var adapter: BookCopyAdapter
    private lateinit var buttonLogout: Button


    private val viewModel: BookViewModel by viewModels()

    private lateinit var tokenManager: TokenManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tokenManager = TokenManager(this)

        // Cek apakah user sudah login
        if (tokenManager.getToken() == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        setContentView(R.layout.activity_main)

        buttonLogout = findViewById(R.id.buttonLogout)

        val editTextSearch = findViewById<EditText>(R.id.editTextSearch)

        editTextSearch.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                adapter.filter(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        buttonLogout.setOnClickListener {
            tokenManager.clearToken()

            startActivity(
                Intent(this, LoginActivity::class.java)
            )

            finish()
        }

        recyclerViewBooks = findViewById(R.id.recyclerViewBooks)

        adapter = BookCopyAdapter(emptyList()) { bookId ->
            openBookDetail(bookId)
        }

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
                    adapter.updateData(bookCopies)
                }
            }
        }
    }

    private fun observeError() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.error.collect { errorMessage ->
                    if (errorMessage != null) {
                        android.util.Log.e(
                            "LIBRI_API",
                            "Error: $errorMessage"
                        )
                    }
                }
            }
        }
    }

    private fun openBookDetail(bookId: String) {
        val intent = Intent(this, BookDetailActivity::class.java)
        intent.putExtra("BOOK_ID", bookId)
        startActivity(intent)
    }
}