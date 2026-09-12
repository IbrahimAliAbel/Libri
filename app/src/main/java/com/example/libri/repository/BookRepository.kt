package com.example.libri.repository

import com.example.libri.BookCopy
import com.example.libri.data.api.RetrofitClient
import com.example.libri.Book

class BookRepository {

    suspend fun getBookCopies(): List<BookCopy> {
        return RetrofitClient.api.getBookCopies()
    }

    suspend fun getBookById(id: String): Book {
        return RetrofitClient.api.getBookById(id)
    }
}