package com.example.hrautomation.presentation.model.social.event

import android.text.SpannableString
import com.example.hrautomation.presentation.base.delegates.BaseListItem

data class EventMaterialItem(
    override val id: Long,
    val text: SpannableString
) : BaseListItem