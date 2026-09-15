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

class BorrowingActivity : AppCompatActivity() {

    private val viewModel: BookViewModel by viewModels()

    private lateinit var rvBorrowings: RecyclerView
    private lateinit var tvBack: TextView
    private lateinit var tokenManager: TokenManager
    private lateinit var adapter: BorrowingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_borrowing)

        tokenManager = TokenManager(this)

        tvBack = findViewById(R.id.tvBack)
        rvBorrowings = findViewById(R.id.rvBorrowings)

        tvBack.setOnClickListener {
            finish()
        }

        adapter = BorrowingAdapter(emptyList())

        rvBorrowings.layoutManager = LinearLayoutManager(this)
        rvBorrowings.adapter = adapter

        observeBorrowings()

        val token = tokenManager.getToken()

        if (token != null) {
            viewModel.loadBorrowings("Bearer $token")
        }
    }

    private fun observeBorrowings() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.borrowings.collect { borrowings ->
                    adapter.updateData(borrowings)
                }
            }
        }
    }
}