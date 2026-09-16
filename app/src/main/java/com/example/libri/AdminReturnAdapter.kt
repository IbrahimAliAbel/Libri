package com.example.libri

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.widget.Button

class AdminReturnAdapter(
    private var borrowings: List<Borrowing>,
    private val onCompleteReturn: (String) -> Unit
) : RecyclerView.Adapter<AdminReturnAdapter.ReturnViewHolder>() {

    class ReturnViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvBook: TextView =
            itemView.findViewById(R.id.tvAdminReturnBook)

        val tvUser: TextView =
            itemView.findViewById(R.id.tvAdminReturnUser)

        val tvCopy: TextView =
            itemView.findViewById(R.id.tvAdminReturnCopy)

        val tvStatus: TextView =
            itemView.findViewById(R.id.tvAdminReturnStatus)

        val tvDueDate: TextView =
            itemView.findViewById(R.id.tvAdminReturnDueDate)

        val buttonCompleteReturn: Button =
            itemView.findViewById(R.id.buttonCompleteReturn)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ReturnViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.item_admin_return,
                parent,
                false
            )

        return ReturnViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ReturnViewHolder,
        position: Int
    ) {
        val borrowing = borrowings[position]

        holder.tvBook.text = borrowing.book_title
        holder.tvUser.text = "User ID: ${borrowing.user_id}"
        holder.tvCopy.text = "Copy: ${borrowing.book_copy_code}"
        holder.tvStatus.text = "Status: ${borrowing.status}"
        holder.tvDueDate.text =
            "Due date: ${borrowing.due_date ?: "-"}"

        holder.buttonCompleteReturn.setOnClickListener {
            onCompleteReturn(borrowing.id)
        }
    }

    override fun getItemCount(): Int {
        return borrowings.size
    }

    fun updateData(newBorrowings: List<Borrowing>) {
        borrowings = newBorrowings
        notifyDataSetChanged()
    }
}