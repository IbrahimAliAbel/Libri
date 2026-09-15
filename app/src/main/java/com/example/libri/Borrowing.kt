package com.example.libri

data class Borrowing(
    val id: String,
    val user_id: String,
    val book_copy_id: String,
    val status: String,
    val requested_at: String?,
    val approved_at: String?,
    val borrowed_at: String?,
    val due_date: String?,
    val return_requested_at: String?,
    val returned_at: String?,
    val created_at: String,
    val updated_at: String,

    val book_title: String,
    val book_copy_code: String
)