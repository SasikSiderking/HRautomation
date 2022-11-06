package com.example.hrautomation.presentation.view.colleagues

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hrautomation.databinding.EmployeeRecyclerviewItemBinding
import com.example.hrautomation.presentation.model.ColleagueItem

class ColleaguesAdapter(private val onClickListener: OnColleagueClickListener) :
    RecyclerView.Adapter<ColleaguesAdapter.ViewHolder>() {

    private var dataSet: List<ColleagueItem> = emptyList()

    class ViewHolder(val binding: EmployeeRecyclerviewItemBinding, clickAtPosition: (Int) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener { clickAtPosition(adapterPosition) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = EmployeeRecyclerviewItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding) { onClickListener.onClick(dataSet[it]) }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.employeeName.text = dataSet[position].name
        holder.binding.employeePost.text = dataSet[position].post
        holder.binding.employeeProject.text = dataSet[position].project
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    fun update(data: List<ColleagueItem>) {
        dataSet = data
        notifyDataSetChanged()
    }
}