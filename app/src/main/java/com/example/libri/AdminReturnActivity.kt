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

class AdminReturnActivity : AppCompatActivity() {

    private val viewModel: BookViewModel by viewModels()

    private lateinit var rvAdminReturns: RecyclerView
    private lateinit var tvBack: TextView
    private lateinit var tokenManager: TokenManager
    private lateinit var adapter: AdminReturnAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_return)

        tokenManager = TokenManager(this)

        tvBack = findViewById(R.id.tvBack)
        rvAdminReturns = findViewById(R.id.rvAdminReturns)

        tvBack.setOnClickListener {
            finish()
        }

        adapter = AdminReturnAdapter(
            emptyList(),
            onCompleteReturn = { borrowingId ->

                val token = tokenManager.getToken()

                if (token != null) {
                    viewModel.completeReturn(
                        "Bearer $token",
                        borrowingId
                    )
                }
            }
        )

        rvAdminReturns.layoutManager = LinearLayoutManager(this)
        rvAdminReturns.adapter = adapter

        observeReturns()

        val token = tokenManager.getToken()

        if (token != null) {
            viewModel.loadBorrowings("Bearer $token")
        }
    }

    private fun observeReturns() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                viewModel.borrowings.collect { borrowings ->
                    val returnRequests = borrowings.filter {
                        it.status == "RETURN_PENDING"
                    }

                    adapter.updateData(returnRequests)
                }
            }
        }
    }
}