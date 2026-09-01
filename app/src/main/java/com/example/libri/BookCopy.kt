package com.example.libri

data class BookCopy(
    val id: String,
    val book_id: String,
    val code: String,
    val status: String,
    val created_at: String,
    val updated_at: String
)