package com.example.hrautomation.presentation.view.colleagues

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hrautomation.databinding.EmployeeRecyclerviewItemBinding
import com.example.hrautomation.domain.model.Employee

class ColleaguesAdapter(private var dataSet: List<Employee>, private val onClickListener: OnEmployeeClickListener) :
    RecyclerView.Adapter<ColleaguesAdapter.ViewHolder>() {
    class ViewHolder(binding: EmployeeRecyclerviewItemBinding, clickAtPosition: (Int) -> Unit) : RecyclerView.ViewHolder(binding.root) {
        val nameTextView: TextView
        val postTextView: TextView

        init {
            // Define click listener for the ViewHolder's View.
            binding.root.setOnClickListener { clickAtPosition(adapterPosition) }
            nameTextView = binding.employeeName
            postTextView = binding.employeePost
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = EmployeeRecyclerviewItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding) { onClickListener.onClick(dataSet[it]) }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nameTextView.text = dataSet[position].name
        holder.postTextView.text = dataSet[position].post
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    fun update(data: List<Employee>) {
        dataSet = data
        notifyDataSetChanged()
    }
}