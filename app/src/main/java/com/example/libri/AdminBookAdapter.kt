package com.example.libri

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdminBookAdapter(
    private var books: List<Book>,
    private val onEditClick: (Book) -> Unit,
    private val onDeleteClick: (Book) -> Unit
) : RecyclerView.Adapter<AdminBookAdapter.BookViewHolder>() {

    class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvTitle: TextView =
            itemView.findViewById(R.id.tvAdminBookTitle)

        val tvIsbn: TextView =
            itemView.findViewById(R.id.tvAdminBookIsbn)

        val tvPublisher: TextView =
            itemView.findViewById(R.id.tvAdminBookPublisher)

        val tvYear: TextView =
            itemView.findViewById(R.id.tvAdminBookYear)

        val buttonEdit: TextView =
            itemView.findViewById(R.id.buttonEditBook)

        val buttonDelete: TextView =
            itemView.findViewById(R.id.buttonDeleteBook)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BookViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.item_admin_book,
                parent,
                false
            )

        return BookViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: BookViewHolder,
        position: Int
    ) {
        val book = books[position]

        holder.tvTitle.text = book.title
        holder.tvIsbn.text = "ISBN: ${book.isbn ?: "-"}"
        holder.tvPublisher.text = "Publisher: ${book.publisher ?: "-"}"
        holder.tvYear.text = "Year: ${book.published_year ?: "-"}"

        holder.buttonEdit.setOnClickListener {
            onEditClick(book)
        }

        holder.buttonDelete.setOnClickListener {
            onDeleteClick(book)
        }
    }

    override fun getItemCount(): Int {
        return books.size
    }

    fun updateData(newBooks: List<Book>) {
        books = newBooks
        notifyDataSetChanged()
    }
}