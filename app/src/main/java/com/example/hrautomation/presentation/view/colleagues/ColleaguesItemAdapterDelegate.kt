package com.example.hrautomation.presentation.view.colleagues

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.hrautomation.databinding.ItemEmployeeBinding
import com.example.hrautomation.presentation.base.delegates.BaseItemAdapterDelegate
import com.example.hrautomation.presentation.base.delegates.BaseListItem
import com.example.hrautomation.presentation.base.delegates.ClickableViewHolder
import com.example.hrautomation.presentation.base.delegates.OnViewHolderClickListener
import com.example.hrautomation.presentation.model.colleagues.ListedColleagueItem
import com.example.hrautomation.presentation.view.colleagues.ColleaguesItemAdapterDelegate.ColleagueViewHolder

class ColleaguesItemAdapterDelegate(private val onColleagueClickListener: OnColleagueClickListener) :
    BaseItemAdapterDelegate<ListedColleagueItem, ColleagueViewHolder>(),
    OnViewHolderClickListener<ColleagueViewHolder> {
    override fun isForViewType(item: BaseListItem): Boolean {
        return item is ListedColleagueItem
    }

    override fun onCreateViewHolder(parent: ViewGroup): ColleagueViewHolder {
        val binding = ItemEmployeeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ColleagueViewHolder(binding, this)
    }

    override fun onBind(item: ListedColleagueItem, holder: ColleagueViewHolder, payloads: List<Any>) {
        holder.name.text = item.name
        holder.post.text = item.post
    }

    override fun onViewHolderClick(view: View, holder: ColleagueViewHolder) {
        val item = getItemForViewHolder(holder)
        onColleagueClickListener.onClick(item)
    }

    class ColleagueViewHolder(binding: ItemEmployeeBinding, clickListener: OnViewHolderClickListener<ColleagueViewHolder>) :
        ClickableViewHolder<ColleagueViewHolder>(binding.root, clickListener) {
        val name: TextView
        val post: TextView

        init {
            name = binding.employeeName
            post = binding.employeePost
        }
    }
}