package com.example.libri.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.libri.Book
import com.example.libri.BookCopy
import com.example.libri.repository.BookRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.libri.BorrowRequest
import com.example.libri.Borrowing
import retrofit2.HttpException
import org.json.JSONObject

class BookViewModel : ViewModel() {

    private val repository = BookRepository()

    private val _bookCopies = MutableStateFlow<List<BookCopy>>(emptyList())
    val bookCopies: StateFlow<List<BookCopy>> = _bookCopies

    private val _borrowing = MutableStateFlow<Borrowing?>(null)
    val borrowing: StateFlow<Borrowing?> = _borrowing

    private val _borrowings = MutableStateFlow<List<Borrowing>>(emptyList())
    val borrowings: StateFlow<List<Borrowing>> = _borrowings

    private val _bookDetail = MutableStateFlow<Book?>(null)
    val bookDetail: StateFlow<Book?> = _bookDetail

    private val _bookCopiesByBook = MutableStateFlow<List<BookCopy>>(emptyList())
    val bookCopiesByBook: StateFlow<List<BookCopy>> = _bookCopiesByBook

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun loadBookCopies() {
        viewModelScope.launch {
            try {
                _error.value = null
                _bookCopies.value = repository.getBookCopies()
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun loadBookDetail(id: String) {
        viewModelScope.launch {
            try {
                _error.value = null
                _bookDetail.value = repository.getBookById(id)
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun loadBookCopiesByBookId(bookId: String) {
        viewModelScope.launch {
            try {
                _error.value = null
                _bookCopiesByBook.value =
                    repository.getBookCopiesByBookId(bookId)
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun createBorrowing(
        token: String,
        bookCopyId: String
    ) {
        viewModelScope.launch {
            try {
                _error.value = null

                _borrowing.value = repository.createBorrowing(
                    token,
                    BorrowRequest(bookCopyId)
                )
            } catch (e: Exception) {
                if (e is HttpException) {
                    val errorBody = e.response()?.errorBody()?.string()

                    _error.value = try {
                        JSONObject(errorBody ?: "").getString("message")
                    } catch (jsonException: Exception) {
                        e.message
                    }
                } else {
                    _error.value = e.message
                }

                android.util.Log.e(
                    "LIBRI_BORROW",
                    "Borrow failed: ${_error.value}",
                    e
                )
            }
        }
    }

    fun loadBorrowings(token: String) {
        viewModelScope.launch {
            try {
                _error.value = null
                _borrowings.value = repository.getBorrowings(token)
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun approveBorrowing(token: String, id: String) {
        viewModelScope.launch {
            try {
                _error.value = null

                repository.approveBorrowing(token, id)

                // Refresh daftar borrowing setelah approve
                _borrowings.value = repository.getBorrowings(token)

            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun rejectBorrowing(token: String, id: String) {
        viewModelScope.launch {
            try {
                _error.value = null

                repository.rejectBorrowing(token, id)

                // Refresh daftar borrowing setelah reject
                _borrowings.value = repository.getBorrowings(token)

            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun requestReturn(token: String, id: String) {
        viewModelScope.launch {
            try {
                _error.value = null

                repository.requestReturn(token, id)

                // Refresh daftar borrowing setelah request return
                _borrowings.value = repository.getBorrowings(token)

            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun completeReturn(token: String, id: String) {
        viewModelScope.launch {
            try {
                _error.value = null

                repository.completeReturn(token, id)

                // Refresh daftar borrowing setelah return selesai
                _borrowings.value = repository.getBorrowings(token)

            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
}