package com.example.hrautomation.utils.ui.switcher.base

import com.example.hrautomation.utils.ui.switcher.base.interpolator.CrossSwitchInterpolator
import com.example.hrautomation.utils.ui.switcher.base.processor.StateViewProcessor

data class SwitchAnimationParams(
    val delay: Long = DEFAULT_ANIMATION_DELAY,
    val duration: Long = DEFAULT_ANIMATION_DURATION,
    val interpolator: StateViewProcessor.SwitchInterpolator = CrossSwitchInterpolator()
) {

    private companion object {
        const val DEFAULT_ANIMATION_DURATION = 500L
        const val DEFAULT_ANIMATION_DELAY = 0L
    }
}