package com.example.libri.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.libri.Book
import com.example.libri.BookCopy
import com.example.libri.repository.BookRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BookViewModel : ViewModel() {

    private val repository = BookRepository()

    private val _bookCopies = MutableStateFlow<List<BookCopy>>(emptyList())
    val bookCopies: StateFlow<List<BookCopy>> = _bookCopies

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
}