package com.example.libri.repository

import com.example.libri.BookCopy
import com.example.libri.data.api.RetrofitClient
import com.example.libri.Book
import com.example.libri.BorrowRequest
import com.example.libri.Borrowing

class BookRepository {

    suspend fun getBookCopies(): List<BookCopy> {
        return RetrofitClient.api.getBookCopies()
    }

    suspend fun getBookCopiesByBookId(bookId: String): List<BookCopy> {
        return RetrofitClient.api.getBookCopiesByBookId(bookId)
    }

    suspend fun getBookById(id: String): Book {
        return RetrofitClient.api.getBookById(id)
    }

    suspend fun createBorrowing(
        token: String,
        request: BorrowRequest
    ): Borrowing {
        return RetrofitClient.api.createBorrowing(token, request)
    }

    suspend fun getBorrowings(token: String): List<Borrowing> {
        return RetrofitClient.api.getBorrowings(token)
    }

    suspend fun approveBorrowing(
        token: String,
        id: String
    ): Borrowing =
        RetrofitClient.api.approveBorrowing(token, id)

    suspend fun rejectBorrowing(
        token: String,
        id: String
    ): Borrowing =
        RetrofitClient.api.rejectBorrowing(token, id)
}