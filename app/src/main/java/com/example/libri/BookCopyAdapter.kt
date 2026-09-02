package com.example.libri

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BookCopyAdapter(
    private var bookCopies: List<BookCopy>
) : RecyclerView.Adapter<BookCopyAdapter.BookCopyViewHolder>() {

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
    }

    override fun getItemCount(): Int {
        return bookCopies.size
    }
}