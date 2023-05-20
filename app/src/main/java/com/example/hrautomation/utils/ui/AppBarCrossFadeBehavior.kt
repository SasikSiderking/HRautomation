package com.example.hrautomation.utils.ui

import android.view.View
import androidx.appcompat.widget.Toolbar
import com.google.android.material.appbar.AppBarLayout

class AppBarCrossFadeBehavior(
    private val toolbar: Toolbar,
    private val startViews: List<View> = emptyList(),
    private val endViews: List<View> = emptyList()
) : AppBarLayout.OnOffsetChangedListener {

    override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
        val totalRange = appBarLayout.totalScrollRange
        when {
            verticalOffset == 0 -> startScrolling(appBarLayout)
            -verticalOffset == totalRange -> endScrolling(appBarLayout)
            else -> processScrolling(appBarLayout, -verticalOffset / totalRange.toFloat())
        }
    }

    private fun startScrolling(appBarLayout: AppBarLayout) {
        appBarLayout.elevation = 0F
        startViews.applyAlpha(1F)
        endViews.applyAlpha(0F)
    }

    private fun endScrolling(appBarLayout: AppBarLayout) {
        appBarLayout.elevation = 4F
        startViews.applyAlpha(0F)
        endViews.applyAlpha(1F)
    }

    private fun processScrolling(appBarLayout: AppBarLayout, fraction: Float) {
        val newStartAlpha = fraction
        val newEndAlpha = 1 - newStartAlpha

        startViews.applyAlpha(newEndAlpha)
        endViews.applyAlpha(newStartAlpha)
    }

    private fun List<View>.applyAlpha(alpha: Float) {
        forEach { it.alpha = alpha }
    }
}
