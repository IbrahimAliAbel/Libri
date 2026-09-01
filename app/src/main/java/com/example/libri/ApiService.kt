package com.example.libri

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {

    @GET("book-copies")
    suspend fun getBookCopies(): List<BookCopy>
}

object RetrofitClient {

    private const val BASE_URL = "http://127.0.0.1:3000/"

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}