package com.example.libri.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.libri.BookCopy
import com.example.libri.repository.BookRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BookViewModel : ViewModel() {

    private val repository = BookRepository()

    private val _bookCopies = MutableStateFlow<List<BookCopy>>(emptyList())
    val bookCopies: StateFlow<List<BookCopy>> = _bookCopies

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
}