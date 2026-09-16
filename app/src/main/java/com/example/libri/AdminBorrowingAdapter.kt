package com.example.libri

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.widget.Button

class AdminBorrowingAdapter(
    private var borrowings: List<Borrowing>,
    private val onApprove: (String) -> Unit,
    private val onReject: (String) -> Unit
) : RecyclerView.Adapter<AdminBorrowingAdapter.BorrowingViewHolder>() {

    class BorrowingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvBook: TextView =
            itemView.findViewById(R.id.tvAdminBorrowingBook)

        val tvUser: TextView =
            itemView.findViewById(R.id.tvAdminBorrowingUser)

        val tvCopy: TextView =
            itemView.findViewById(R.id.tvAdminBorrowingCopy)

        val tvStatus: TextView =
            itemView.findViewById(R.id.tvAdminBorrowingStatus)

        val tvDueDate: TextView =
            itemView.findViewById(R.id.tvAdminBorrowingDueDate)

        val buttonApprove: Button =
            itemView.findViewById(R.id.buttonApprove)

        val buttonReject: Button =
            itemView.findViewById(R.id.buttonReject)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BorrowingViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.item_admin_borrowing,
                parent,
                false
            )

        return BorrowingViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: BorrowingViewHolder,
        position: Int
    ) {
        val borrowing = borrowings[position]

        holder.tvBook.text = borrowing.book_title
        holder.tvUser.text = "User ID: ${borrowing.user_id}"
        holder.tvCopy.text = "Copy: ${borrowing.book_copy_code}"
        holder.tvStatus.text = "Status: ${borrowing.status}"
        holder.tvDueDate.text =
            "Due date: ${borrowing.due_date ?: "-"}"
        if (borrowing.status == "PENDING") {

            holder.buttonApprove.visibility = View.VISIBLE
            holder.buttonReject.visibility = View.VISIBLE

            holder.buttonApprove.setOnClickListener {
                onApprove(borrowing.id)
            }

            holder.buttonReject.setOnClickListener {
                onReject(borrowing.id)
            }

        } else {

            holder.buttonApprove.visibility = View.GONE
            holder.buttonReject.visibility = View.GONE
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