package com.example.libri.data.api

import com.example.libri.BookCopy
import retrofit2.http.GET

interface ApiService {

    @GET("book-copies")
    suspend fun getBookCopies(): List<BookCopy>
}
