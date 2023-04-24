package com.example.hrautomation.presentation.view.social.filter

import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener

class InputTextTouchListener(private val actionUpCallback: () -> Unit) : OnTouchListener {
    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        when (motionEvent.action) {
            MotionEvent.ACTION_UP -> {
                view.performClick()
                view.requestFocus()
                actionUpCallback()
                return true
            }
        }
        return false
    }
}