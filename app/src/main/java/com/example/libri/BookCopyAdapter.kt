package com.example.libri

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class BookCopyAdapter(
    private var bookCopies: List<BookCopy>,
    private val onBookClick: (String) -> Unit
) : RecyclerView.Adapter<BookCopyAdapter.BookCopyViewHolder>() {

    private var allBookCopies: List<BookCopy> = bookCopies

    class BookCopyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvBookCode: TextView = itemView.findViewById(R.id.tvBookCode)
        val tvBookStatus: TextView = itemView.findViewById(R.id.tvBookStatus)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BookCopyViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_book, parent, false)

        return BookCopyViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: BookCopyViewHolder,
        position: Int
    ) {
        val bookCopy = bookCopies[position]

        holder.tvBookCode.text = bookCopy.code
        holder.tvBookStatus.text = bookCopy.status

        if (bookCopy.status == "AVAILABLE") {
            holder.tvBookStatus.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    android.R.color.holo_green_dark
                )
            )
        } else {
            holder.tvBookStatus.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    android.R.color.holo_red_dark
                )
            )
        }

        holder.itemView.setOnClickListener {
            onBookClick(bookCopy.book_id)
        }
    }

    override fun getItemCount(): Int {
        return bookCopies.size
    }

    fun updateData(newBookCopies: List<BookCopy>) {
        allBookCopies = newBookCopies
        bookCopies = newBookCopies
        notifyDataSetChanged()
    }

    fun filter(query: String) {
        bookCopies = if (query.isBlank()) {
            allBookCopies
        } else {
            allBookCopies.filter {
                it.code.contains(query, ignoreCase = true)
            }
        }

        notifyDataSetChanged()
    }
}