package com.example.hrautomation.utils.ui.switcher

import android.view.View
import com.example.hrautomation.utils.ui.switcher.base.StateQueueManager

class ContentLoadingSettings(
    private val contentViews: List<View> = emptyList(),
    private val errorViews: List<View> = emptyList(),
    private val loadingViews: List<View> = emptyList(),
    override val initState: ContentLoadingState = ContentLoadingState.LOADING,
) : StateQueueManager.Settings<ContentLoadingState> {

    override fun setup() {
        (contentViews + errorViews + loadingViews).hideViews()
        getViewsFromState(initState).showViews()
    }

    override fun getViewsFromState(state: ContentLoadingState): List<View> {
        return when (state) {
            ContentLoadingState.CONTENT -> contentViews
            ContentLoadingState.ERROR -> errorViews
            ContentLoadingState.LOADING -> loadingViews
            ContentLoadingState.NONE -> emptyList()
        }
    }

    private fun List<View>.hideViews() {
        forEach {
            it.alpha = ZERO_ALPHA
            it.visibility = View.INVISIBLE
        }
    }

    private fun List<View>.showViews() {
        forEach {
            it.alpha = FULL_ALPHA
            it.visibility = View.VISIBLE
        }
    }

    private companion object {
        const val FULL_ALPHA = 1F
        const val ZERO_ALPHA = 0F
    }
}

enum class ContentLoadingState : StateQueueManager.ContentState {
    CONTENT,
    LOADING,
    ERROR,
    NONE
}