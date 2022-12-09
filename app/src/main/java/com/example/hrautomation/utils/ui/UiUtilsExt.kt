package com.example.hrautomation.utils.ui

import android.content.Context
import android.content.res.Resources
import androidx.annotation.Dimension
import androidx.annotation.Px
import androidx.fragment.app.Fragment
import kotlin.math.roundToInt

@Dimension(unit = Dimension.DP)
annotation class Dp

@Px
fun Context.dpToPx(@Dp dp: Float): Int {
    return dpToPx(dp, resources)
}

/**
 * This method converts dp unit to equivalent pixels, depending on device density.
 */
@Px
fun dpToPx(@Dp dp: Float, resources: Resources): Int {
    return (dp * resources.displayMetrics.density).roundToInt()
}

@Px
fun Fragment.dpToPx(@Dp dp: Float): Int {
    return dpToPx(dp, resources)
}