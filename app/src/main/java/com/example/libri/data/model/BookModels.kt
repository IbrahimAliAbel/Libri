package com.example.libri.data.model

data class CreateBookRequest(
    val title: String,
    val description: String?,
    val isbn: String?,
    val publisher: String?,
    val published_year: Int?,
    val cover_url: String?,
    val category_id: String?
)

data class Category(
    val id: String,
    val name: String
)