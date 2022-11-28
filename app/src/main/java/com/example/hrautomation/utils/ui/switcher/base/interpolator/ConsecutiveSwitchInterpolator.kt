package com.example.hrautomation.utils.ui.switcher.base.interpolator

import com.example.hrautomation.utils.ui.switcher.base.processor.StateViewProcessor
import kotlin.math.max


/**
 * @author m.a.kovalev
 *
 * The interpolator which represents consecutive changing strategy.
 * WARNING: You must keep in mind, that each part of animation will take a half of all duration,
 *          so if you have 500ms of full animation duration, switch in and switch out animations will both take 250ms.
 * ex. [fromViewsValue, toViewsValue]:
 *     [1.0, 0.0] -> [0.5, 0.0] -> [0.0, 0.0] -> [0.0, 0.5] -> [0.0, 1.0]
 */
class ConsecutiveSwitchInterpolator : StateViewProcessor.SwitchInterpolator {

    override fun calculateSwitchInValue(ratio: Float): Float {
        val compressedRatio = 2F * ratio - 1F // from -1 to 1
        return max(0F, compressedRatio)
    }

    override fun calculateSwitchOutValue(ratio: Float): Float {
        val compressedRatio = 2F * ratio - 1F // from -1 to 1
        return max(0F, -compressedRatio)
    }
}