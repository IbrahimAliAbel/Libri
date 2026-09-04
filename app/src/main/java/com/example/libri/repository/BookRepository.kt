package com.example.libri.repository

import com.example.libri.BookCopy
import com.example.libri.data.api.RetrofitClient

class BookRepository {

    suspend fun getBookCopies(): List<BookCopy> {
        return RetrofitClient.api.getBookCopies()
    }
}