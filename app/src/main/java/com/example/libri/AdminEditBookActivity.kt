package com.example.libri

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.libri.data.model.Category
import com.example.libri.data.model.CreateBookRequest
import com.example.libri.viewmodel.BookViewModel
import kotlinx.coroutines.launch

class AdminEditBookActivity : AppCompatActivity() {

    private val viewModel: BookViewModel by viewModels()

    private lateinit var tokenManager: TokenManager

    private lateinit var tvBack: TextView
    private lateinit var editTextTitle: EditText
    private lateinit var editTextDescription: EditText
    private lateinit var editTextIsbn: EditText
    private lateinit var editTextPublisher: EditText
    private lateinit var editTextPublishedYear: EditText
    private lateinit var editTextCoverUrl: EditText
    private lateinit var spinnerCategory: Spinner
    private lateinit var buttonUpdateBook: Button

    private var categories = emptyList<Category>()

    private var bookId: String? = null

    private var selectedCategoryId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_edit_book)

        tokenManager = TokenManager(this)

        bookId = intent.getStringExtra("book_id")

        if (bookId == null) {
            Toast.makeText(
                this,
                "Book ID tidak ditemukan",
                Toast.LENGTH_LONG
            ).show()

            finish()
            return
        }

        tvBack = findViewById(R.id.tvBack)
        editTextTitle = findViewById(R.id.editTextTitle)
        editTextDescription = findViewById(R.id.editTextDescription)
        editTextIsbn = findViewById(R.id.editTextIsbn)
        editTextPublisher = findViewById(R.id.editTextPublisher)
        editTextPublishedYear = findViewById(R.id.editTextPublishedYear)
        editTextCoverUrl = findViewById(R.id.editTextCoverUrl)
        spinnerCategory = findViewById(R.id.spinnerCategory)
        buttonUpdateBook = findViewById(R.id.buttonUpdateBook)

        tvBack.setOnClickListener {
            finish()
        }

        buttonUpdateBook.setOnClickListener {
            updateBook()
        }

        observeViewModel()

        viewModel.loadCategories()
        viewModel.loadBookById(bookId!!)
    }

    private fun updateBook() {
        val id = bookId ?: return

        val title = editTextTitle.text.toString().trim()
        val description = editTextDescription.text.toString().trim()
        val isbn = editTextIsbn.text.toString().trim()
        val publisher = editTextPublisher.text.toString().trim()
        val publishedYearText = editTextPublishedYear.text.toString().trim()
        val coverUrl = editTextCoverUrl.text.toString().trim()

        if (title.isEmpty()) {
            editTextTitle.error = "Title is required"
            return
        }

        if (categories.isEmpty()) {
            Toast.makeText(
                this,
                "Category belum tersedia",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        val selectedCategory =
            categories[spinnerCategory.selectedItemPosition]

        val categoryId = selectedCategory.id

        val publishedYear = if (publishedYearText.isEmpty()) {
            null
        } else {
            publishedYearText.toIntOrNull()
        }

        if (publishedYearText.isNotEmpty() && publishedYear == null) {
            editTextPublishedYear.error = "Invalid year"
            return
        }

        val token = tokenManager.getToken()

        if (token == null) {
            Toast.makeText(
                this,
                "Admin session expired",
                Toast.LENGTH_LONG
            ).show()
            return
        }

        val request = CreateBookRequest(
            title = title,
            description = description.ifEmpty { null },
            isbn = isbn.ifEmpty { null },
            publisher = publisher.ifEmpty { null },
            published_year = publishedYear,
            cover_url = coverUrl.ifEmpty { null },
            category_id = categoryId
        )

        viewModel.updateBook(
            token = "Bearer $token",
            id = id,
            request = request
        )
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                launch {
                    viewModel.categories.collect { categoryList ->

                        categories = categoryList

                        val categoryNames =
                            categories.map { it.name }

                        val adapter = ArrayAdapter(
                            this@AdminEditBookActivity,
                            android.R.layout.simple_spinner_item,
                            categoryNames
                        )

                        adapter.setDropDownViewResource(
                            android.R.layout.simple_spinner_dropdown_item
                        )

                        spinnerCategory.adapter = adapter

                        val currentBook = viewModel.selectedBook.value

                        if (currentBook != null) {
                            val selectedIndex = categories.indexOfFirst {
                                it.id == currentBook.category_id
                            }

                            if (selectedIndex >= 0) {
                                spinnerCategory.setSelection(selectedIndex)
                            }
                        }
                    }
                }

                launch {
                    viewModel.bookCreated.collect { book ->

                        if (book != null) {
                            Toast.makeText(
                                this@AdminEditBookActivity,
                                "Book updated successfully",
                                Toast.LENGTH_SHORT
                            ).show()

                            finish()
                        }
                    }
                }

                launch {
                    viewModel.error.collect { errorMessage ->

                        if (errorMessage != null) {
                            Toast.makeText(
                                this@AdminEditBookActivity,
                                errorMessage,
                                Toast.LENGTH_LONG
                            ).show()

                            viewModel.clearError()
                        }
                    }
                }

                launch {
                    viewModel.selectedBook.collect { book ->
                        if (book != null) {
                            editTextTitle.setText(book.title)
                            editTextDescription.setText(book.description ?: "")
                            editTextIsbn.setText(book.isbn ?: "")
                            editTextPublisher.setText(book.publisher ?: "")
                            editTextPublishedYear.setText(
                                book.published_year?.toString() ?: ""
                            )
                            editTextCoverUrl.setText(book.cover_url ?: "")
                        }
                    }
                }
            }
        }
    }
}