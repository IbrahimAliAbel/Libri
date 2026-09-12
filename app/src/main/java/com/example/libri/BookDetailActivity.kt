package com.example.libri

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.libri.viewmodel.BookViewModel
import kotlinx.coroutines.launch

class BookDetailActivity : AppCompatActivity() {

    private val viewModel: BookViewModel by viewModels()

    private lateinit var tvBack: TextView
    private lateinit var tvBookTitle: TextView
    private lateinit var tvBookPublisher: TextView
    private lateinit var tvBookYear: TextView
    private lateinit var tvBookIsbn: TextView
    private lateinit var tvBookDescription: TextView
    private lateinit var tvBookAvailability: TextView
    private lateinit var buttonBorrow: Button
    private lateinit var ivBookCover: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_book_detail)

        tvBack = findViewById(R.id.tvBack)
        tvBookTitle = findViewById(R.id.tvBookTitle)
        tvBookPublisher = findViewById(R.id.tvBookPublisher)
        tvBookYear = findViewById(R.id.tvBookYear)
        tvBookIsbn = findViewById(R.id.tvBookIsbn)
        tvBookDescription = findViewById(R.id.tvBookDescription)
        tvBookAvailability = findViewById(R.id.tvBookAvailability)
        buttonBorrow = findViewById(R.id.buttonBorrow)
        ivBookCover = findViewById(R.id.ivBookCover)

        tvBack.setOnClickListener {
            finish()
        }

        val bookId = intent.getStringExtra("BOOK_ID")

        if (bookId == null) {
            finish()
            return
        }

        observeBookDetail()
        observeBookCopies()
        viewModel.loadBookDetail(bookId)
        viewModel.loadBookCopiesByBookId(bookId)
    }

    private fun observeBookDetail() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.bookDetail.collect { book ->

                    if (book != null) {
                        tvBookTitle.text = book.title

                        tvBookPublisher.text =
                            "Publisher: ${book.publisher ?: "-"}"

                        tvBookYear.text =
                            "Published: ${book.published_year ?: "-"}"

                        tvBookIsbn.text =
                            "ISBN: ${book.isbn ?: "-"}"

                        tvBookDescription.text =
                            book.description ?: "No description available"

                        tvBookAvailability.text =
                            "Availability: Check physical copies"
                    }
                }
            }
        }
    }

    private fun observeBookCopies() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.bookCopiesByBook.collect { bookCopies ->
                    val availableCount = bookCopies.count {
                        it.status == "AVAILABLE"
                    }

                    val totalCount = bookCopies.size

                    tvBookAvailability.text =
                        "Availability: $availableCount of $totalCount copies available"

                    buttonBorrow.isEnabled = availableCount > 0
                }
            }
        }
    }
}