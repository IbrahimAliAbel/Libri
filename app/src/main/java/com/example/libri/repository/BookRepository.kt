package com.example.libri.repository

import com.example.libri.BookCopy
import com.example.libri.data.api.RetrofitClient
import com.example.libri.Book
import com.example.libri.BorrowRequest
import com.example.libri.Borrowing
import com.example.libri.data.model.CreateBookRequest
import com.example.libri.data.model.Category

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

    suspend fun requestReturn(
        token: String,
        id: String
    ): Borrowing =
        RetrofitClient.api.requestReturn(token, id)

    suspend fun completeReturn(
        token: String,
        id: String
    ): Borrowing =
        RetrofitClient.api.completeReturn(token, id)

    suspend fun getBooks(): List<Book> =
        RetrofitClient.api.getBooks()

    suspend fun createBook(
        token: String,
        request: CreateBookRequest
    ): Book =
        RetrofitClient.api.createBook(token, request)

    suspend fun getCategories(): List<Category> =
        RetrofitClient.api.getCategories()

    suspend fun updateBook(
        token: String,
        id: String,
        request: CreateBookRequest
    ): Book =
        RetrofitClient.api.updateBook(token, id, request)
}