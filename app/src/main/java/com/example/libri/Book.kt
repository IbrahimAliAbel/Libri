package com.example.libri

data class Book(
    val id: String,
    val title: String,
    val description: String?,
    val isbn: String?,
    val publisher: String?,
    val published_year: Int?,
    val cover_url: String?,
    val category_id: String?,
    val created_at: String,
    val updated_at: String
)