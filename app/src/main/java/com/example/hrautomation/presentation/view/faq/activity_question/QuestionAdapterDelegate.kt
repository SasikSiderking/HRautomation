package com.example.hrautomation.presentation.view.faq.activity_question

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.hrautomation.databinding.ItemFaqQuestionBinding
import com.example.hrautomation.presentation.base.delegates.BaseItemAdapterDelegate
import com.example.hrautomation.presentation.base.delegates.BaseListItem
import com.example.hrautomation.presentation.base.delegates.ClickableViewHolder
import com.example.hrautomation.presentation.base.delegates.OnViewHolderClickListener
import com.example.hrautomation.presentation.model.FaqQuestionItem
import com.example.hrautomation.presentation.view.faq.activity_question.QuestionAdapterDelegate.FaqQuestionItemViewHolder

class QuestionAdapterDelegate : BaseItemAdapterDelegate<FaqQuestionItem, FaqQuestionItemViewHolder>(), OnViewHolderClickListener<FaqQuestionItemViewHolder> {
    override fun isForViewType(item: BaseListItem): Boolean {
        return item is FaqQuestionItem
    }

    override fun onCreateViewHolder(parent: ViewGroup): FaqQuestionItemViewHolder {
        val binding = ItemFaqQuestionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FaqQuestionItemViewHolder(binding, this)
    }

    override fun onBind(item: FaqQuestionItem, holder: FaqQuestionItemViewHolder, payloads: List<Any>) {
        holder.title.text = item.title
        holder.description.text = item.description
    }

    override fun onViewHolderClick(view: View, holder: FaqQuestionItemViewHolder) {
    }

    class FaqQuestionItemViewHolder(binding: ItemFaqQuestionBinding, clickListener: OnViewHolderClickListener<FaqQuestionItemViewHolder>) :
        ClickableViewHolder<FaqQuestionItemViewHolder>(binding.root, clickListener) {
        val title: TextView
        val description: TextView

        init {
            title = binding.title
            description = binding.description
        }
    }
}