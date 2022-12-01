package com.example.hrautomation.utils.ui.switcher.base.processor

import android.animation.ValueAnimator
import android.view.View
import androidx.core.animation.doOnCancel
import androidx.core.animation.doOnEnd
import com.example.hrautomation.utils.ui.switcher.base.SwitchAnimationParams

class SetStateViewProcessor() : StateViewProcessor() {

    private var currentStateAnimator: ValueAnimator? = null
    private var isCancelled: Boolean = false
    private var cancelMode: CancelAnimationMode = CancelAnimationMode.BACKWARD

    override fun cancelAnimation(mode: CancelAnimationMode) {
        cancelMode = mode
        currentStateAnimator?.cancel()
    }

    override fun processStateTransition(
        fromViews: List<View>,
        toViews: List<View>,
        animationParams: SwitchAnimationParams?,
        switchStateListener: SwitchStateListener?,
    ) {
        prepareViewsForSwitch(fromViews, toViews)

        if (animationParams != null) {
            currentStateAnimator = ValueAnimator.ofFloat(ZERO_ALPHA, FULL_ALPHA).apply {
                startDelay = animationParams.delay
                duration = animationParams.duration
                val interpolator = animationParams.interpolator

                addUpdateListener { value ->
                    val ratio = value.animatedValue as Float

                    val toViewsAlpha = interpolator.calculateSwitchInValue(ratio)
                    toViews.setAlpha(toViewsAlpha)
                }
                doOnCancel {
                    isCancelled = true
                }
                doOnEnd {
                    if (isCancelled) {
                        when (cancelMode) {
                            CancelAnimationMode.FORWARD -> setTerminateState(toViews)
                            CancelAnimationMode.BACKWARD -> setTerminateState(fromViews)
                        }
                        isCancelled = false
                        switchStateListener?.onSwitchingCanceled(cancelMode)
                    } else {
                        setTerminateState(toViews)
                        switchStateListener?.onStateSwitched()
                    }
                }
            }
            currentStateAnimator?.start()
            switchStateListener?.onSwitchingStarted()
        } else {
            switchStateListener?.onSwitchingStarted()
            setTerminateState(toViews)
            switchStateListener?.onStateSwitched()
        }
    }

    private fun setTerminateState(views: List<View>) {
        views.setupParams(alpha = FULL_ALPHA, visibility = View.VISIBLE)
    }

    private fun prepareViewsForSwitch(fromViews: List<View>, toViews: List<View>) {
        fromViews.setupParams(alpha = ZERO_ALPHA, visibility = View.INVISIBLE)
        toViews.setupParams(alpha = ZERO_ALPHA, visibility = View.VISIBLE)
    }
}