package com.example.libri

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BorrowingAdapter(
    private var borrowings: List<Borrowing>
) : RecyclerView.Adapter<BorrowingAdapter.BorrowingViewHolder>() {

    class BorrowingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvBorrowingBook: TextView =
            itemView.findViewById(R.id.tvBorrowingBook)

        val tvBorrowingCopy: TextView =
            itemView.findViewById(R.id.tvBorrowingCopy)

        val tvBorrowingStatus: TextView =
            itemView.findViewById(R.id.tvBorrowingStatus)

        val tvBorrowingDueDate: TextView =
            itemView.findViewById(R.id.tvBorrowingDueDate)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BorrowingViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_borrowing, parent, false)

        return BorrowingViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: BorrowingViewHolder,
        position: Int
    ) {
        val borrowing = borrowings[position]

        holder.tvBorrowingBook.text =
            borrowing.book_title

        holder.tvBorrowingCopy.text =
            "Copy: ${borrowing.book_copy_code}"

        holder.tvBorrowingStatus.text =
            "Status: ${borrowing.status}"

        holder.tvBorrowingDueDate.text =
            "Due date: ${borrowing.due_date ?: "-"}"
    }

    override fun getItemCount(): Int = borrowings.size

    fun updateData(newBorrowings: List<Borrowing>) {
        borrowings = newBorrowings
        notifyDataSetChanged()
    }
}