package com.example.libri.data.api

import com.example.libri.Book
import com.example.libri.BookCopy
import com.example.libri.data.model.LoginRequest
import com.example.libri.data.model.LoginResponse
import com.example.libri.data.model.MeResponse
import com.example.libri.data.model.RegisterRequest
import com.example.libri.data.model.RegisterResponse
import com.example.libri.BorrowRequest
import com.example.libri.Borrowing
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.PUT
import com.example.libri.data.model.CreateBookRequest
import com.example.libri.data.model.Category

interface ApiService {

    @GET("book-copies")
    suspend fun getBookCopies(): List<BookCopy>

    @GET("book-copies/book/{book_id}")
    suspend fun getBookCopiesByBookId(
        @Path("book_id") bookId: String
    ): List<BookCopy>

    @GET("books/{id}")
    suspend fun getBookById(
        @Path("id") id: String
    ): Book

    @POST("auth/register")
    suspend fun register(
        @Body request: RegisterRequest
    ): RegisterResponse

    @POST("auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse

    @GET("auth/me")
    suspend fun getMe(
        @Header("Authorization") token: String
    ): MeResponse

    @POST("borrowings")
    suspend fun createBorrowing(
        @Header("Authorization") token: String,
        @Body request: BorrowRequest
    ): Borrowing

    @GET("borrowings")
    suspend fun getBorrowings(
        @Header("Authorization") token: String
    ): List<Borrowing>

    @PUT("borrowings/{id}/approve")
    suspend fun approveBorrowing(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Borrowing

    @PUT("borrowings/{id}/reject")
    suspend fun rejectBorrowing(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Borrowing

    @PUT("borrowings/{id}/request-return")
    suspend fun requestReturn(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Borrowing

    @PUT("borrowings/{id}/complete-return")
    suspend fun completeReturn(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Borrowing

    @GET("books")
    suspend fun getBooks(): List<Book>

    @POST("books")
    suspend fun createBook(
        @Header("Authorization") token: String,
        @Body request: CreateBookRequest
    ): Book

    @GET("categories")
    suspend fun getCategories(): List<Category>
}