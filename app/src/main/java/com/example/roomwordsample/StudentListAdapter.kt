package com.example.roomwordsample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class StudentListAdapter(listener: ListAction) : ListAdapter<Student, StudentListAdapter.StudentViewHolder>(StudentsComparator()) {

    private var listAction: ListAction = listener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        return StudentViewHolder.create(parent, listAction)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    class StudentViewHolder(itemView: View, listAction: ListAction) : RecyclerView.ViewHolder(itemView) {
        private val studentItemView: TextView = itemView.findViewById(R.id.textView)
        private val deleteButton: Button = itemView.findViewById(R.id.button)
        private val context = listAction

        fun bind(student: Student) {
            studentItemView.text = student.name
            deleteButton.setOnClickListener {
                context.onDelete(student)
            }
            studentItemView.setOnClickListener {
                context.onEdit(student)
            }
        }

        companion object {
            fun create(parent: ViewGroup, listAction: ListAction): StudentViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)
                return StudentViewHolder(view, listAction)
            }
        }
    }

    class StudentsComparator : DiffUtil.ItemCallback<Student>() {
        override fun areItemsTheSame(oldItem: Student, newItem: Student): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Student, newItem: Student): Boolean {
            return oldItem.name == newItem.name
        }
    }

    interface ListAction {
        fun onDelete(student: Student)
        fun onEdit(student: Student)
    }
}