package com.example.hrautomation.presentation.view.colleagues

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hrautomation.R
import com.example.hrautomation.domain.model.Employee

class ColleaguesFragmentRecyclerViewAdapter(private var dataSet: List<Employee>, private val onClickListener: OnEmployeeClickListener) :
    RecyclerView.Adapter<ColleaguesFragmentRecyclerViewAdapter.ViewHolder>() {
    class ViewHolder(view: View, clickAtPosition: (Int) -> Unit) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView
        val postTextView: TextView

        init {
            // Define click listener for the ViewHolder's View.
            view.setOnClickListener { clickAtPosition(adapterPosition) }
            nameTextView = view.findViewById(R.id.employeeName)
            postTextView = view.findViewById(R.id.employeePost)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.employee_recyclerview_item, parent, false)
        return ViewHolder(view) { onClickListener.clickListener(dataSet[it]) }
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