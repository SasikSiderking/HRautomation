package com.example.hrautomation.utils.ui.switcher.base.processor

import android.view.View
import com.example.hrautomation.utils.ui.switcher.base.SwitchAnimationParams

abstract class StateViewProcessor {

    abstract fun processStateTransition(
        fromViews: List<View>,
        toViews: List<View>,
        animationParams: SwitchAnimationParams?,
        switchStateListener: SwitchStateListener?
    )

    abstract fun cancelAnimation(mode: CancelAnimationMode)

    protected fun List<View>.setAlpha(alpha: Float) {
        this.forEach { it.alpha = alpha }
    }

    protected fun List<View>.setupParams(alpha: Float, visibility: Int) {
        this.forEach {
            it.visibility = visibility
            it.alpha = alpha
        }
    }

    protected companion object {
        const val FULL_ALPHA = 1F
        const val ZERO_ALPHA = 0F
    }

    enum class CancelAnimationMode {
        FORWARD,
        BACKWARD
    }

    interface SwitchStateListener {

        fun onStateSwitched()

        fun onSwitchingCanceled(mode: CancelAnimationMode)

        fun onSwitchingStarted()
    }

    interface SwitchInterpolator {

        /**
         * Calculates switch animation value for "from views" based on [ratio]
         * @param ratio the value from 0.0 to 1.0 represents animation path
         */
        fun calculateSwitchInValue(ratio: Float): Float

        /**
         * Calculates switch animation value for "to views" based on [ratio]
         * @param ratio the value from 0.0 to 1.0 represents animation path
         */
        fun calculateSwitchOutValue(ratio: Float): Float
    }
}