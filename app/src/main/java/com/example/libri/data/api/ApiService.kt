package com.example.libri.data.api

import com.example.libri.Book
import com.example.libri.BookCopy
import com.example.libri.data.model.LoginRequest
import com.example.libri.data.model.LoginResponse
import com.example.libri.data.model.MeResponse
import com.example.libri.data.model.RegisterRequest
import com.example.libri.data.model.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @GET("book-copies")
    suspend fun getBookCopies(): List<BookCopy>

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
}